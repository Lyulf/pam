package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.repository.IWeatherRepository
import javax.inject.Inject

class DownloadWeatherDataUseCase @Inject constructor(
    private val repository: IWeatherRepository
) {
    suspend fun invoke(locationId: Long): Boolean {
        return repository.updateData(locationId)
    }
}