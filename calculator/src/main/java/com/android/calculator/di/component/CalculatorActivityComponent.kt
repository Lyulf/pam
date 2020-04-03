package com.android.calculator.di.component

import com.android.calculator.di.module.CalculatorActivityModule
import com.android.calculator.di.scope.ActivityScope
import com.android.calculator.presentation.view.CalculatorActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [CalculatorActivityModule::class])
interface CalculatorActivityComponent {
    fun inject(activity: CalculatorActivity)
}