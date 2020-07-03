package com.android.pam.astro_weather.domain.model.weather

import org.threeten.bp.LocalDate

data class DailyForecast(
    val date: LocalDate,
    val temp_day: Float? = null,
    val temp_night: Float? = null,
    val description: String? = null,
    val icon: String? = null
)
