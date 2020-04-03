package com.android.calculator.presentation.presenter

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

abstract class BasePresenter<View> : ViewModel(), LifecycleObserver {
    protected var view: View? = null
        private set
    private var viewLifecycle: Lifecycle? = null

    open fun attachView(view: View, viewLifecycle: Lifecycle) {
        this.view = view
        this.viewLifecycle = viewLifecycle

        viewLifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun onViewDestroyed() {
        view = null
        viewLifecycle = null
        Log.d("BasePresenter", "Destroyed")
    }
}