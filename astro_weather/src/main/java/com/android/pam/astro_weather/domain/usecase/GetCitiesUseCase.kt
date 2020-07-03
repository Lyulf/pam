package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.repository.ICityRepository
import javax.inject.Inject

data class GetCitiesUseCase @Inject constructor(
    private val repository: ICityRepository
) {
    suspend fun invoke(
        city: String? = null,
        state: String? = null,
        country: String? = null,
        favourite: Boolean? = null,
        id: Long? = null
    ) = repository.getCities(city, state, country, favourite, id)
}