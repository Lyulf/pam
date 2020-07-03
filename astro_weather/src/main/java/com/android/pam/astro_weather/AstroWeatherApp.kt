package com.android.pam.astro_weather

import android.app.Application
import com.android.pam.astro_weather.dagger.component.ApplicationComponent
import com.android.pam.astro_weather.dagger.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AstroWeatherApp : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    private lateinit var component: ApplicationComponent

    override fun onCreate() {
        component = DaggerApplicationComponent.builder()
            .applicationBind(this)
            .build()
        component.inject(this)
        super.onCreate()
    }

    fun appComponent(): ApplicationComponent {
        return component
    }

    override fun androidInjector(): AndroidInjector<Any>
            = dispatchingAndroidInjector
}
