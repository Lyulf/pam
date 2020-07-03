package com.android.pam.astro_weather.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class CityEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    @ColumnInfo(name = "location")
    val coord: CoordinatesEntity,
    var favourite: Boolean = false
)