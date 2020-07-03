package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.model.weather.WeatherData
import com.android.pam.astro_weather.domain.repository.IWeatherRepository
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val repository: IWeatherRepository
) {
    fun invoke(locationId: Long): WeatherData? {
        return repository.getWeather(locationId)
    }
}