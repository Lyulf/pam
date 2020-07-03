package com.android.pam.astro_weather.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.pam.astro_weather.data.entity.weather.OneCall

@Entity(tableName = "weather_table")
data class WeatherEntity(
    val weather: OneCall,
    val location: String,
    @PrimaryKey
    @ColumnInfo(name = "city_id")
    val id: Long = -1
)