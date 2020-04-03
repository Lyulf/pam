package com.android.calculator.domain.usecase

import com.android.calculator.domain.repository.IExpressionRepository
import javax.inject.Inject

class SetExpression @Inject constructor(
    private val expressionRepository: IExpressionRepository){

    fun invoke(value: String) {
        expressionRepository.setExpression(value)
    }

}