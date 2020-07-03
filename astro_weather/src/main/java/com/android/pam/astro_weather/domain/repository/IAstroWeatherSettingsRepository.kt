package com.android.pam.astro_weather.domain.repository

import com.android.pam.astro_weather.domain.model.location.Coordinates
import com.android.pam.astro_weather.domain.model.settings.Settings

interface IAstroWeatherSettingsRepository {
    fun setSettings(settings: Settings)
    fun getSettings(): Settings
    suspend fun getLocation(): Coordinates?
    fun getRefreshRate(): Double
}