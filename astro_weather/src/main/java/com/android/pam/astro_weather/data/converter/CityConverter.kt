package com.android.pam.astro_weather.data.converter

import com.android.pam.astro_weather.data.converter.CoordinatesConverter.toCoordinates
import com.android.pam.astro_weather.data.converter.CoordinatesConverter.toCoordinatesEntity
import com.android.pam.astro_weather.data.entity.CityEntity
import com.android.pam.astro_weather.domain.model.location.City

object CityConverter {
    @JvmStatic
    fun CityEntity.toCity(): City {
        return City(id, name, state, country, coord.toCoordinates(), favourite)
    }

    @JvmStatic
    fun City.toCityEntity(): CityEntity {
        return CityEntity(id, name, state, country, coordinates.toCoordinatesEntity(), favourite)
    }
}