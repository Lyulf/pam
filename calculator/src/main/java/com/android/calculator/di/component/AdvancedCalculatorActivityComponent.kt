package com.android.calculator.di.component

import com.android.calculator.di.module.AdvancedCalculatorActivityModule
import com.android.calculator.di.scope.ActivityScope
import com.android.calculator.presentation.view.AdvancedCalculatorActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [AdvancedCalculatorActivityModule::class])
interface AdvancedCalculatorActivityComponent {
    fun inject(activity: AdvancedCalculatorActivity)
}