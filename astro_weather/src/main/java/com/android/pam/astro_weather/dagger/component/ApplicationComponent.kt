package com.android.pam.astro_weather.dagger.component

import android.app.Application
import android.content.Context
import com.android.pam.astro_weather.AstroWeatherApp
import com.android.pam.astro_weather.dagger.module.ConfigurationModule
import com.android.pam.astro_weather.dagger.module.RoomModule
import com.android.pam.astro_weather.dagger.scope.ApplicationScope
import com.android.pam.astro_weather.data.dao.CityDao
import com.android.pam.astro_weather.data.dao.WeatherDao
import com.android.pam.astro_weather.data.database.AstroWeatherRoomDatabase
import com.android.pam.astro_weather.domain.repository.IAstroWeatherSettingsRepository
import com.android.pam.astro_weather.domain.repository.ICityRepository
import com.android.pam.astro_weather.domain.usecase.GetSettingsUseCase
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@ApplicationScope
@Component(modules = [
    AndroidInjectionModule::class,
    RoomModule::class,
    ConfigurationModule::class
])
interface ApplicationComponent {
    fun context(): Context
    fun astroWeatherRoomDatabase(): AstroWeatherRoomDatabase
    fun cityDao(): CityDao
    fun weatherDao(): WeatherDao
    fun astrologySettingsRepository(): IAstroWeatherSettingsRepository
    fun cityRepository(): ICityRepository
    fun getSettingsUseCase(): GetSettingsUseCase

    fun inject(app: AstroWeatherApp)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder
    }
}