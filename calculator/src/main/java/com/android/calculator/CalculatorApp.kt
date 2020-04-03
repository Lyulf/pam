package com.android.calculator

import android.app.Application
import android.util.Log
import com.android.calculator.di.component.ApplicationComponent
import com.android.calculator.di.component.DaggerApplicationComponent

class CalculatorApp : Application() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        Log.d("CalculatorApp_onCreate", "Start")
        super.onCreate()
        createAppComponent()
        Log.d("CalculatorApp_onCreate", "End")
    }

    private fun createAppComponent() {
        component = DaggerApplicationComponent.create()
        component.inject(this)
    }
}