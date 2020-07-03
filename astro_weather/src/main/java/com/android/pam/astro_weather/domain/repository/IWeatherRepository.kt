package com.android.pam.astro_weather.domain.repository

import com.android.pam.astro_weather.domain.model.weather.WeatherData

interface IWeatherRepository {
    suspend fun updateData(locationId: Long): Boolean
    fun getWeather(locationId: Long): WeatherData?
}