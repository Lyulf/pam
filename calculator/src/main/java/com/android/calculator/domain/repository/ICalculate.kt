package com.android.calculator.domain.repository

import com.android.calculator.domain.entity.Expression

interface ICalculate {
    fun evaluate(expression: Expression): String
}