package com.android.pam.astro_weather.data.entity.weather

import com.android.pam.astro_weather.data.entity.weather.current.CurrentWeather
import com.android.pam.astro_weather.data.entity.weather.daily.DailyForecast

data class OneCall(
    val lat: Float,
    val lon: Float,
    val timezone: String,
    val timezone_offset: Long,
    val current: CurrentWeather,
    val daily: List<DailyForecast> = arrayListOf()
)