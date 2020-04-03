package com.android.calculator.di.module

import com.android.calculator.domain.usecase.ClearExpression
import com.android.calculator.domain.usecase.EvaluateExpression
import com.android.calculator.domain.usecase.GetExpression
import com.android.calculator.domain.usecase.SetExpression
import com.android.calculator.presentation.contract.IAdvancedCalculatorContract
import com.android.calculator.presentation.presenter.AdvancedCalculatorPresenter
import dagger.Module
import dagger.Provides

@Module
class AdvancedCalculatorActivityModule(private val activity: IAdvancedCalculatorContract.IView) {
    @Provides
    fun provideAdvancedCalculatorActivity(): IAdvancedCalculatorContract.IView =
        activity

    @Provides
    fun provideAdvancedCalculatorPresenter(
        clearExpression: ClearExpression,
        evaluateExpression: EvaluateExpression,
        getExpression: GetExpression,
        setExpression: SetExpression
    ): IAdvancedCalculatorContract.IPresenter =
        AdvancedCalculatorPresenter(
            clearExpression,
            evaluateExpression,
            getExpression,
            setExpression
        )
}