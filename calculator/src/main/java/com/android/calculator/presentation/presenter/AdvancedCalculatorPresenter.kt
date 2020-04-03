package com.android.calculator.presentation.presenter

import androidx.lifecycle.Lifecycle
import com.android.calculator.data.common.isMathFunction
import com.android.calculator.data.common.isOperator
import com.android.calculator.domain.usecase.ClearExpression
import com.android.calculator.domain.usecase.EvaluateExpression
import com.android.calculator.domain.usecase.GetExpression
import com.android.calculator.domain.usecase.SetExpression
import com.android.calculator.presentation.contract.IAdvancedCalculatorContract
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AdvancedCalculatorPresenter @Inject constructor(
    @Suppress("CanBeParameter") private val clearExpression: ClearExpression,
    @Suppress("CanBeParameter") private val evaluateExpression: EvaluateExpression,
    private val getExpression: GetExpression,
    @Suppress("CanBeParameter") private val setExpression: SetExpression)
    : BasePresenter<IAdvancedCalculatorContract.IView>(),
    IAdvancedCalculatorContract.IPresenter,
    CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val simpleCalculatorPresenter = CalculatorPresenter (
        clearExpression,
        evaluateExpression,
        getExpression,
        setExpression
    )

    override fun attachView(view: IAdvancedCalculatorContract.IView, viewLifecycle: Lifecycle) {
        super.attachView(view, viewLifecycle)
        simpleCalculatorPresenter.attachView(view, viewLifecycle)
    }

    override fun addToken(token: String) {
        launch {
            when {
                token.isMathFunction() -> addMathFunc(token)
                else -> simpleCalculatorPresenter.addToken(token)
            }
        }
    }

    private suspend fun addMathFunc(function: String) {
        val expression = getExpression.invoke().value
        if(expression.isEmpty()) {
            view?.invalidOperationError()
            return
        }
        val builder = StringBuilder(expression)
        val lastToken = expression.last()
        when {
            lastToken.isOperator() || lastToken == '(' -> {
                view?.invalidOperationError()
                return
            }
            lastToken == ')' -> {
                var mathFuncStartIndex = expression.indexOfOpeningParenthesis(expression.length - 1)
                if(mathFuncStartIndex == -1) {
                    view?.invalidOperationError()
                    return
                }
                val expressionBeforeParentheses = expression.substring(0, mathFuncStartIndex)
                val lastTokenBeforeParenthesesPos =
                    expressionBeforeParentheses.indexOfLast { c -> c == ' ' } + 1
                if(expressionBeforeParentheses.substring(lastTokenBeforeParenthesesPos).isMathFunction()) {
                    mathFuncStartIndex = lastTokenBeforeParenthesesPos
                }
                val lastValue = expression.substring(mathFuncStartIndex)
                builder.setLength(mathFuncStartIndex)
                builder.append("$function($lastValue)")

            }
            else -> {
                val indexOfLastOp = expression.indexOfLastOperator()
                val lastValue = expression.substring(indexOfLastOp + 1).trim()
                builder.setLength(indexOfLastOp + 1)
                if(indexOfLastOp != -1) {
                    builder.append(' '.toString())
                }
                builder.append("$function($lastValue)")
            }
        }
        setExpression(builder.toString())
    }

    override fun onDestroy() {
        launch {
            simpleCalculatorPresenter.onDestroy()
            onViewDestroyed()
        }
    }

    override fun displayExpression() {
        launch {
            simpleCalculatorPresenter.displayExpression()
        }
    }

    override fun setExpression(value: String) {
        launch {
            simpleCalculatorPresenter.setExpression(value)
        }
    }

    override fun evaluateExpression() {
        launch {
            simpleCalculatorPresenter.evaluateExpression()
        }
    }

    override fun negate() {
        launch {
            simpleCalculatorPresenter.negate()
        }
    }

    override fun back() {
        launch {
            var expression = getExpression.invoke().value.trimEnd()
            if(expression.isBlank()) {
                return@launch
            } else if(expression.last() == ')') {
                val openingParenthesesPos = expression.indexOfOpeningParenthesis(expression.length - 1)
                if(openingParenthesesPos != -1) {
                    val subExpression = expression.substring(0, openingParenthesesPos)
                    val separatorPos = subExpression.indexOfLast { c -> c == ' ' }
                    if(subExpression.substring(separatorPos + 1).isMathFunction()) {
                        expression = (
                                expression.substring(0, separatorPos + 1) +
                                expression.substring(
                                    openingParenthesesPos + 1,
                                    expression.length - 1
                                )).trim()
                        setExpression(expression)
                        return@launch
                    }
                }
            }
            simpleCalculatorPresenter.back()
        }
    }

    private suspend fun String.indexOfLastOperator(): Int {
        return withContext(Dispatchers.Default + job) { indexOfLast { c -> c.isOperator() } }
    }

    override fun clear() {
        launch {
            simpleCalculatorPresenter.clear()
        }
    }

    private suspend fun String.indexOfOpeningParenthesis(closingParenthesisPos: Int): Int {
        return withContext(Dispatchers.Default) {
            var it = if(closingParenthesisPos <= length) closingParenthesisPos else length
            var noOpenedParentheses = -1
            while(--it >= 0) {
                val token = get(it)
                if(token == '(') {
                    if(++noOpenedParentheses == 0) {
                        return@withContext it
                    }
                } else if(token == ')') {
                    noOpenedParentheses--
                }
            }
            return@withContext -1
        }
    }
}