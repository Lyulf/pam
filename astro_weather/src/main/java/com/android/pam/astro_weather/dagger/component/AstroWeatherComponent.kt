package com.android.pam.astro_weather.dagger.component

import androidx.fragment.app.FragmentActivity
import com.android.pam.astro_weather.dagger.module.ActivitiesModule
import com.android.pam.astro_weather.dagger.module.AstrologyModule
import com.android.pam.astro_weather.dagger.module.WeatherModule
import com.android.pam.astro_weather.dagger.scope.ActivityScope
import com.android.pam.astro_weather.presentation.view.activity.AstroWeatherActivity
import com.android.pam.astro_weather.presentation.view.fragment.*
import dagger.Component

@ActivityScope
@Component(
    modules = [
        ActivitiesModule::class,
        AstrologyModule::class,
        WeatherModule::class
    ],
    dependencies = [ApplicationComponent::class]
)
interface AstroWeatherComponent {
    fun fragmentActivity(): FragmentActivity

    fun inject(astroWeatherActivity: AstroWeatherActivity)
    fun inject(fragment: SunFragment)
    fun inject(fragment: MoonFragment)
    fun inject(fragment: BasicWeatherFragment)
    fun inject(fragment: AdditionalWeatherFragment)
    fun inject(fragment: ForecastFragment)
}