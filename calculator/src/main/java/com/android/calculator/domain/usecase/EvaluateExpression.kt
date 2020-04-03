package com.android.calculator.domain.usecase

import com.android.calculator.domain.entity.Expression
import com.android.calculator.domain.repository.ICalculate
import javax.inject.Inject

class EvaluateExpression @Inject constructor(
    private val evaluator: ICalculate) {

    fun invoke(expression: Expression): String {
        return evaluator.evaluate(expression)
    }
}