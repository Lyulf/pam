package com.android.pam.astro_weather.domain.model.weather

data class WeatherData(
    val basic: BasicWeatherData? = null,
    val additional: AdditionalWeatherData? = null,
    val forecast: List<DailyForecast>? = null
)