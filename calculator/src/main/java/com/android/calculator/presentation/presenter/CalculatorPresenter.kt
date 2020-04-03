package com.android.calculator.presentation.presenter

import com.android.calculator.data.common.isOperator
import com.android.calculator.data.common.isVariable
import com.android.calculator.data.exception.InvalidExpression
import com.android.calculator.domain.usecase.ClearExpression
import com.android.calculator.domain.usecase.EvaluateExpression
import com.android.calculator.domain.usecase.GetExpression
import com.android.calculator.domain.usecase.SetExpression
import com.android.calculator.presentation.contract.ICalculatorContract
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

open class CalculatorPresenter @Inject constructor(
    private val clearExpression: ClearExpression,
    private val evaluateExpression: EvaluateExpression,
    private val getExpression: GetExpression,
    private val setExpression: SetExpression)
    : BasePresenter<ICalculatorContract.IView>(),
    ICalculatorContract.IPresenter,
    CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun displayExpression() {
        launch {
            val expression = getExpression.invoke()
            view?.display(expression.value)
        }
    }

    override fun setExpression(value: String) {
        launch {
            setExpression.invoke(value.trim())
            displayExpression()
        }
    }

    override fun addToken(token: String) {
        launch {
            when {
                token.isBlank() -> return@launch
                token.first().isDigit() -> addDigit(token.first())
                token.first().isOperator() -> addOperator(token.first())
                token.first() == '(' -> addOpeningParenthesis()
                token.first() == ')' -> addClosingParenthesis()
                token.isVariable() -> addVariable(token)
                token.first() == '.' -> addDot()
                token.first() == '%' -> addPercent()
            }
        }
    }

    private fun addDigit(digit: Char) {
        val expression = getExpression.invoke().value
        val builder = StringBuilder(expression)
        val lastToken = expression.lastOrNull()
        when {
            lastToken == null -> {}
            lastToken.isDigit() -> {}
            lastToken == '(' -> {}
            lastToken == '.' -> {}
            else -> builder.append(' '.toString())
        }
        builder.append(digit.toString())
        setExpression(builder.toString())
    }

    private fun addVariable(variable: String) {
        val expression = getExpression.invoke().value
        val builder = StringBuilder(expression)
        when (expression.lastOrNull()) {
            null -> {}
            '(' -> {}
            else -> builder.append(' '.toString())
        }
        builder.append(variable)
        setExpression(builder.toString())
    }

    private fun addOperator(op: Char) {
        val expression = getExpression.invoke().value
        val builder = StringBuilder(expression)
        val lastToken = expression.lastOrNull()
        when {
            lastToken == null -> {}
            lastToken.isOperator() -> {
                builder.setLength(builder.length - 1)
            }
            else -> builder.append(' '.toString())

        }
        builder.append(op.toString())
        setExpression(builder.toString())
    }

    private fun addOpeningParenthesis() {
        val expression = getExpression.invoke().value
        val builder = StringBuilder(expression)
        val lastToken = expression.lastOrNull()
        if(lastToken?.isLetterOrDigit() == true) {
            view?.invalidOperationError()
            return
        } else if(lastToken?.isOperator() == true) {
            builder.append(' '.toString())
        }
        builder.append('('.toString())
        setExpression(builder.toString())
    }

    private fun addClosingParenthesis() {
        val expression = getExpression.invoke().value
        val builder = StringBuilder(expression)
        val lastToken = expression.lastOrNull()
        if(lastToken?.isOperator() == true) {
            view?.invalidOperationError()
            return
        }
        builder.append(')'.toString())
        setExpression(builder.toString())
    }

    private fun addDot() {
        addDigit('.')
    }

    private fun addPercent() {
        val expression = getExpression.invoke().value
        val builder = StringBuilder(expression)
        val lastToken = expression.lastOrNull()
        if(lastToken?.isDigit() == false) {
            view?.invalidOperationError()
            return
        }
        builder.append('%'.toString())
        setExpression(builder.toString())
    }


    override fun evaluateExpression() {
         launch {
             val expression = getExpression.invoke()
             try {
                 val result = withContext(Dispatchers.Default + job) { evaluateExpression.invoke(expression) }
                 view?.display(result)
             } catch (e: InvalidExpression) {
                 view?.resultError()
             }
        }
    }

    override fun negate() {
        launch {
            var expression = getExpression.invoke().value
            if(expression.isBlank()) {
                return@launch
            }
            val lastSeparatorPos = expression.indexOfLast { c -> c == ' '}

            val expressionBefore = expression.substring(0, lastSeparatorPos + 1)
            val middleToken = expression.get(lastSeparatorPos + 1)
            val expressionAfter = expression.substring(lastSeparatorPos + 2)
            expression = when {
                middleToken == '-' -> {
                    "$expressionBefore$expressionAfter"
                }
                middleToken.isOperator() -> {
                    return@launch
                }
                else -> {
                    "$expressionBefore-$middleToken$expressionAfter"
                }
            }
            setExpression(expression)
        }
    }

    override fun back() {
        launch {
            val expression = getExpression.invoke().value
            val builder = java.lang.StringBuilder(expression)
            when {
                builder.lastOrNull()?.isLetter() == true -> {
                    while(builder.lastOrNull()?.isLetter() == true) {
                        builder.setLength(builder.length - 1)
                    }
                }
                else -> builder.setLength((builder.length - 1).coerceAtLeast(0))
            }
            val lastToken = builder.lastOrNull()
            if(lastToken == '-') {
                builder.setLength(builder.length - 1)
            }
            setExpression(builder.trim().toString())
        }
    }

    override fun clear() {
        launch {
            clearExpression.invoke()
            displayExpression()
        }
    }

    override fun onDestroy() {
        launch {
            clearExpression.invoke()
            onViewDestroyed()
        }
    }

    private suspend fun String.indexOfLastOperator(): Int {
        return withContext(Dispatchers.Default) { indexOfLast { c -> c.isOperator() } }
    }
}