package com.android.pam.astro_weather.data.repository

import com.android.pam.astro_weather.data.converter.WeatherConverter.toWeatherData
import com.android.pam.astro_weather.data.dao.WeatherDao
import com.android.pam.astro_weather.domain.model.weather.WeatherData
import com.android.pam.astro_weather.domain.repository.IWeatherRepository
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val api: IWeatherApi
) : IWeatherRepository {
    override suspend fun updateData(locationId: Long): Boolean {
        api.downloadWeatherData(locationId)?.let {
            weatherDao.setWeather(it)
            return true
        } ?: return false
    }

    override fun getWeather(locationId: Long): WeatherData? {
        return weatherDao.getWeather(locationId)?.toWeatherData()
    }

}