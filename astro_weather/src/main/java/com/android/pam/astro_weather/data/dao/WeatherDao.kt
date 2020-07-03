package com.android.pam.astro_weather.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.pam.astro_weather.data.entity.WeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_table WHERE city_id is :id")
    fun getWeather(id: Long): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setWeather(weather: WeatherEntity)
}