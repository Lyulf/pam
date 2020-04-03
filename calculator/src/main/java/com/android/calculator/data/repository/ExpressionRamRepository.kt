package com.android.calculator.data.repository

import android.util.Log
import com.android.calculator.domain.entity.Expression
import com.android.calculator.domain.repository.IExpressionRepository

class ExpressionRamRepository(
    private var expression: Expression = Expression("")
) : IExpressionRepository {

    override fun setExpression(value: String) {
        expression = Expression(value)
        Log.d("set_expression", expression.value)
    }

    override fun getExpression(): Expression {
        Log.d("get_expression", expression.value)
        return expression
    }

    override fun clearExpression() {
        expression = Expression("")
        Log.d("clear_expression", expression.value)
    }

    override fun onDestroy() {
        clearExpression()
    }
}