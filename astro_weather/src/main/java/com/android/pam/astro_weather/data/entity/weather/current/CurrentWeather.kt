package com.android.pam.astro_weather.data.entity.weather.current

import com.android.pam.astro_weather.data.entity.weather.common.Weather

data class CurrentWeather(
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Float? = null,
    val feels_like: Float? = null,
    val pressure: Float? = null,
    val humidity: Float? = null,
    val dew_point: Float? = null,
    val clouds: Float? = null,
    val uvi: Float? = null,
    val visibility: Float? = null,
    val wind_speed: Float? = null,
    val wind_gust: Float? = null,
    val wind_deg: Float? = null,
    val rain: Rain? = null,
    val snow: Snow? = null,
    val weather: List<Weather>? = null
)