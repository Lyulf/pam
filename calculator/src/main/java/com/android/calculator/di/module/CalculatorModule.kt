package com.android.calculator.di.module

import com.android.calculator.data.CalculateImpl
import com.android.calculator.data.IValidate
import com.android.calculator.data.ValidateImpl
import com.android.calculator.data.repository.ExpressionRamRepository
import com.android.calculator.domain.repository.ICalculate
import com.android.calculator.domain.repository.IExpressionRepository
import com.android.calculator.domain.usecase.ClearExpression
import com.android.calculator.domain.usecase.EvaluateExpression
import com.android.calculator.domain.usecase.GetExpression
import com.android.calculator.domain.usecase.SetExpression
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
class CalculatorModule {
    @Provides
    @Singleton
    fun provideExpressionRepository(): IExpressionRepository =
        ExpressionRamRepository()

    @Provides
    @Reusable
    fun provideCalculator(validator: IValidate): ICalculate =
        CalculateImpl(validator)

    @Provides
    @Reusable
    fun provideValidator(): IValidate =
        ValidateImpl

    @Provides
    @Reusable
    fun provideGetExpression(repository: IExpressionRepository): GetExpression =
        GetExpression(repository)

    @Provides
    @Reusable
    fun provideSetExpression(repository: IExpressionRepository): SetExpression =
        SetExpression(repository)

    @Provides
    @Reusable
    fun provideClearExpression(repository: IExpressionRepository): ClearExpression =
        ClearExpression(repository)

    @Provides
    @Reusable
    fun provideEvaluateExpression(calculator: ICalculate): EvaluateExpression =
        EvaluateExpression(calculator)
}