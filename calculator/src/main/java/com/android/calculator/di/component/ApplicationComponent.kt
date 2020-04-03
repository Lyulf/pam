package com.android.calculator.di.component

import com.android.calculator.CalculatorApp
import com.android.calculator.data.IValidate
import com.android.calculator.di.module.CalculatorModule
import com.android.calculator.domain.repository.ICalculate
import com.android.calculator.domain.repository.IExpressionRepository
import com.android.calculator.domain.usecase.ClearExpression
import com.android.calculator.domain.usecase.EvaluateExpression
import com.android.calculator.domain.usecase.GetExpression
import com.android.calculator.domain.usecase.SetExpression
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CalculatorModule::class])
interface ApplicationComponent {
    fun expressionRepository(): IExpressionRepository
    fun calculator(): ICalculate
    fun validator(): IValidate
    fun getExpression(): GetExpression
    fun setExpression(): SetExpression
    fun clearExpression(): ClearExpression
    fun evaluateExpression(): EvaluateExpression

    fun inject(app: CalculatorApp)
}
