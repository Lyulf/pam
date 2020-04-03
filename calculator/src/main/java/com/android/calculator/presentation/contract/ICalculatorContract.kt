package com.android.calculator.presentation.contract

import androidx.lifecycle.Lifecycle

interface ICalculatorContract {
    interface IView {
        fun display(value: String)
        fun invalidOperationError()
        fun resultError()
    }

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