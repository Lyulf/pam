package com.android.calculator.data

import com.android.calculator.domain.entity.Expression

interface IValidate {
    fun validateExpression(expression: Expression)
}