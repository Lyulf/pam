package com.android.pam.astro_weather.data.repository

import com.android.pam.astro_weather.data.entity.WeatherEntity

interface IWeatherApi {
    suspend fun downloadWeatherData(locationId: Long): WeatherEntity?
}