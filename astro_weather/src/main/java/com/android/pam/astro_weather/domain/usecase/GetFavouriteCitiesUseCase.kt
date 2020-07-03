package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.model.location.City
import com.android.pam.astro_weather.domain.repository.ICityRepository
import javax.inject.Inject

class GetFavouriteCitiesUseCase @Inject constructor(
    private val repository: ICityRepository
) {
    suspend fun invoke(): List<City> {
        return repository.getFavouriteCities()
    }
}