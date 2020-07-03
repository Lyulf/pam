package com.android.pam.astro_weather.data.converter

import androidx.room.TypeConverter
import com.android.pam.astro_weather.data.entity.WeatherEntity
import com.android.pam.astro_weather.data.entity.weather.OneCall
import com.android.pam.astro_weather.domain.model.weather.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

object WeatherConverter {
    @TypeConverter
    @JvmStatic
    fun OneCall.toJson(): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(OneCall::class.java)
        return adapter.toJson(this)
    }

    @TypeConverter
    @JvmStatic
    fun String.toOneCall(): OneCall? {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(OneCall::class.java)
        return adapter.fromJson(this)
    }

    @JvmStatic
    fun WeatherEntity.toWeatherData(): WeatherData {
        val basic = BasicWeatherData(
            location,
            weather.lat,
            weather.lon,
            ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(weather.current.dt),
                ZoneId.of(weather.timezone)
            ),
            weather.current.temp,
            weather.current.pressure,
            weather.current.weather?.get(0)?.description,
            weather.current.weather?.get(0)?.icon
        )
        val additional = AdditionalWeatherData(
            Wind(weather.current.wind_speed, weather.current.wind_deg),
            weather.current.humidity,
            weather.current.visibility
        )
        val forecasts = weather.daily.map {
            DailyForecast(
                ZonedDateTime.ofInstant(
                    Instant.ofEpochSecond(it.dt),
                    ZoneId.of(weather.timezone)
                ).toLocalDate(),
                it.temp?.day,
                it.temp?.night,
                it.weather?.firstOrNull()?.description,
                it.weather?.firstOrNull()?.icon
            )
        }
        return WeatherData(basic, additional, forecasts)
    }
}