package com.android.pam.astro_weather.data.wrapper

import android.util.Log
import com.android.pam.astro_weather.data.dao.CityDao
import com.android.pam.astro_weather.data.entity.WeatherEntity
import com.android.pam.astro_weather.data.entity.weather.OneCall
import com.android.pam.astro_weather.data.repository.IWeatherApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URL
import javax.inject.Inject

class OpenWeatherMapWrapper @Inject constructor(
    private val dao: CityDao
) : IWeatherApi {
    override suspend fun downloadWeatherData(locationId: Long): WeatherEntity? {
        val city = dao.getCity(locationId) ?: return null
        val response = URL(
            "https://api.openweathermap.org/data/2.5/onecall?" +
                    "lat=${city.coord.lat}&lon=${city.coord.lon}&exclude=minutely,hourly" +
                    "&appid=f8a1c5d8eba81dbe07efa61c35510bf6").readText()
        Log.d("weather_data_json", response)
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(OneCall::class.java)
        return adapter.fromJson(response)?.let {
            WeatherEntity(it, city.name, locationId)
        }

    }
}