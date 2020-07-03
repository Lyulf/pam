package com.android.pam.astro_weather.domain.model.settings

data class Settings(
    val location_id: Long? = null,
    val dataRefreshFrequency: Double = 1.0,
    val units: Units = Units.METRIC
) {
    enum class Units {
        STANDARD,
        METRIC,
        IMPERIAL
    }
}