package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.repository.IAstroWeatherSettingsRepository
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val repository: IAstroWeatherSettingsRepository
) {
    fun invoke(settings: Settings) {
        repository.setSettings(settings)
    }
}