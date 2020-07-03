package com.android.pam.astro_weather.data.repository

import com.android.pam.astro_weather.data.converter.CityConverter.toCity
import com.android.pam.astro_weather.data.converter.CityConverter.toCityEntity
import com.android.pam.astro_weather.data.dao.CityDao
import com.android.pam.astro_weather.domain.model.location.City
import com.android.pam.astro_weather.domain.repository.ICityRepository
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val dao: CityDao
) : ICityRepository {
    override suspend fun getCities(
        city: String?,
        state: String?,
        country: String?,
        favourite: Boolean?,
        id: Long?
    ): List<City> {
        return dao.getCities(city, state, country, favourite, id).map {
            it.toCity()
        }
    }

    override suspend fun getFavouriteCities(): List<City> {
        return dao.getFavourites().map {
            it.toCity()
        }
    }

    override suspend fun insertCities(cities: List<City>) {
        dao.insertCities(cities.map { it.toCityEntity() })
    }

    override suspend fun setCity(city: City) {
        dao.setCity(city.toCityEntity())
    }
}