package com.android.pam.astro_weather.dagger.component

import com.android.pam.astro_weather.dagger.module.ActivitiesModule
import com.android.pam.astro_weather.dagger.module.SplashModule
import com.android.pam.astro_weather.dagger.scope.ActivityScope
import com.android.pam.astro_weather.presentation.view.activity.SplashActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [
        ActivitiesModule::class,
        SplashModule::class
    ],
    dependencies = [ApplicationComponent::class]
)
interface SplashComponent {
    fun inject(activity: SplashActivity)
}