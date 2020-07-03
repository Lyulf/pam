package com.android.pam.astro_weather.domain.repository

import com.android.pam.astro_weather.domain.model.location.City

interface ICityRepository {
    suspend fun getCities(
        city: String? = null,
        state: String? = null,
        country: String? = null,
        favourite: Boolean? = null,
        id: Long? = null
    ): List<City>
    suspend fun getFavouriteCities(): List<City>
    suspend fun insertCities(cities: List<City>)
    suspend fun setCity(city: City)
}