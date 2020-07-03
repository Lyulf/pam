package com.android.pam.astro_weather.dagger.module

import android.app.Application
import android.content.Context
import com.android.pam.astro_weather.dagger.scope.ApplicationScope
import com.android.pam.astro_weather.data.dao.CityDao
import com.android.pam.astro_weather.data.dao.SettingsDao
import com.android.pam.astro_weather.data.repository.AstroWeatherSettingsRepository
import com.android.pam.astro_weather.data.repository.CityRepository
import com.android.pam.astro_weather.domain.repository.IAstroWeatherSettingsRepository
import com.android.pam.astro_weather.domain.repository.ICityRepository
import com.android.pam.astro_weather.domain.usecase.GetSettingsUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides

@Module
class ConfigurationModule {
    @Provides
    @ApplicationScope
    fun provideContext(
        application: Application
    ) : Context = application.applicationContext

    @Provides
    @ApplicationScope
    fun provideGetSettingsUseCase(
        repository: IAstroWeatherSettingsRepository
    ) : GetSettingsUseCase = GetSettingsUseCase(repository)

    @Provides
    @ApplicationScope
    fun provideIAstroWeatherSettingsRepository(
        settingsDao: SettingsDao,
        cityDao: CityDao
    ) : IAstroWeatherSettingsRepository = AstroWeatherSettingsRepository(settingsDao, cityDao)

    @Provides
    @ApplicationScope
    fun provideICityRepository(
        cityDao: CityDao
    ) : ICityRepository = CityRepository(cityDao)

    @Provides
    @ApplicationScope
    fun provideMoshi() : Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory::class.java).build()
}