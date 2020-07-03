package com.android.pam.astro_weather.data.entity.weather.daily

import com.android.pam.astro_weather.data.entity.weather.common.Weather

data class DailyForecast(
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Temp? = null,
    val feels_like: FeelsLike? = null,
    val pressure: Float? = null,
    val humidity: Float? = null,
    val dew_point: Float? = null,
    val wind_speed: Float? = null,
    val wind_gust: Float? = null,
    val wind_deg: Float? = null,
    val clouds: Float? = null,
    val uvi: Float? = null,
    val rain: Float? = null,
    val snow: Float? = null,
    val weather: List<Weather>? = null
)