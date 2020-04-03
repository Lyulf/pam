package com.android.calculator.domain.repository

import com.android.calculator.domain.entity.Expression

interface IExpressionRepository {
    fun setExpression(value: String)
    fun getExpression(): Expression
    fun clearExpression()
    fun onDestroy()
}