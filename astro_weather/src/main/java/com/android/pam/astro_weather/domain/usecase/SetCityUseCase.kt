package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.model.location.City
import com.android.pam.astro_weather.domain.repository.ICityRepository
import javax.inject.Inject

@Suppress("DeferredIsResult")
class SetCityUseCase @Inject constructor(
    private val repository: ICityRepository
) {
    suspend fun invoke(city: City) {
        repository.setCity(city)
    }
}