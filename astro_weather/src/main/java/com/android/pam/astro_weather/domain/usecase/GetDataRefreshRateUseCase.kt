package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.repository.IAstroWeatherSettingsRepository
import javax.inject.Inject

class GetDataRefreshRateUseCase @Inject constructor(
    private val repository: IAstroWeatherSettingsRepository
) {
    fun invoke(): Double = repository.getRefreshRate()
}