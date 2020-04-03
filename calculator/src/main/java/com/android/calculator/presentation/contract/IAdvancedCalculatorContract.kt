package com.android.calculator.presentation.contract

import androidx.lifecycle.Lifecycle

interface IAdvancedCalculatorContract {
    interface IView : ICalculatorContract.IView

    interface IPresenter {
        fun attachView(view: IView, viewLifecycle: Lifecycle)

        fun onDestroy()

        fun addToken(token: String)
        fun displayExpression()
        fun setExpression(value: String)
        fun evaluateExpression()

        fun negate()
        fun back()
        fun clear()
    }
}