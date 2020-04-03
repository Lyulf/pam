package com.android.calculator.domain.usecase

import com.android.calculator.domain.repository.IExpressionRepository
import javax.inject.Inject

class ClearExpression @Inject constructor(
    private val expressionRepository: IExpressionRepository) {

    fun invoke() {
        expressionRepository.clearExpression()
    }
}