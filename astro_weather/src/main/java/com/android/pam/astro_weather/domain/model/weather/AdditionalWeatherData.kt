package com.android.pam.astro_weather.domain.model.weather

data class AdditionalWeatherData(
    val wind: Wind? = null,
    val humidity: Float? = null,
    val visibility: Float? = null
)