package com.android.pam.astro_weather.domain.model.weather

import org.threeten.bp.ZonedDateTime

data class BasicWeatherData(
    val location: String? = null,
    val latitude: Float? = null,
    val longitude: Float? = null,
    val time: ZonedDateTime? = null,
    val temperature: Float? = null,
    val pressure: Float? = null,
    val description: String? = null,
    val icon: String? = null
)