package com.android.calculator.domain.usecase

import com.android.calculator.domain.entity.Expression
import com.android.calculator.domain.repository.IExpressionRepository
import javax.inject.Inject

class GetExpression @Inject constructor(
    private val expressionRepository: IExpressionRepository) {

    fun invoke(): Expression {
        return expressionRepository.getExpression()
    }
}