package com.android.pam.astro_weather.dagger.module

import android.content.Context
import com.android.pam.astro_weather.dagger.scope.ApplicationScope
import com.android.pam.astro_weather.data.dao.CityDao
import com.android.pam.astro_weather.data.dao.SettingsDao
import com.android.pam.astro_weather.data.dao.WeatherDao
import com.android.pam.astro_weather.data.database.AstroWeatherRoomDatabase
import dagger.Module
import dagger.Provides

@Module
class RoomModule {
    @Provides
    @ApplicationScope
    fun provideAstroWeatherRoomDatabase(
        ctx: Context
    ) : AstroWeatherRoomDatabase = AstroWeatherRoomDatabase.getDatabase(ctx)

    @Provides
    @ApplicationScope
    fun provideAstrologyDao(
        roomDatabase: AstroWeatherRoomDatabase
    ) : SettingsDao = roomDatabase.astrologySettingsDao()

    @Provides
    @ApplicationScope
    fun provideWeatherDao(
        roomDatabase: AstroWeatherRoomDatabase
    ) : WeatherDao = roomDatabase.weatherDao()

    @Provides
    @ApplicationScope
    fun provideCityDao(
        roomDatabase: AstroWeatherRoomDatabase
    ) : CityDao = roomDatabase.cityDao()
}