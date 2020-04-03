package com.android.calculator.di.module

import com.android.calculator.domain.usecase.ClearExpression
import com.android.calculator.domain.usecase.EvaluateExpression
import com.android.calculator.domain.usecase.GetExpression
import com.android.calculator.domain.usecase.SetExpression
import com.android.calculator.presentation.contract.ICalculatorContract
import com.android.calculator.presentation.presenter.CalculatorPresenter
import dagger.Module
import dagger.Provides

@Module
class CalculatorActivityModule(private val activity: ICalculatorContract.IView) {
    @Provides
    fun provideCalculatorView(): ICalculatorContract.IView = activity

    @Provides
    fun provideCalculatorPresenter(
        clearExpression: ClearExpression,
        evaluateExpression: EvaluateExpression,
        getExpression: GetExpression,
        setExpression: SetExpression
    ): ICalculatorContract.IPresenter =
        CalculatorPresenter(
            clearExpression,
            evaluateExpression,
            getExpression,
            setExpression
        )
}