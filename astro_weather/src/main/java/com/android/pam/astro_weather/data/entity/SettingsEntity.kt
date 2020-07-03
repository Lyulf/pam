package com.android.pam.astro_weather.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
data class SettingsEntity(
    @ColumnInfo(name = "current_location")
    val location_id: Long?,

    @ColumnInfo(name = "refresh_frequency")
    val frequency: Double,

    val units: Int,

    @PrimaryKey
    val id: Int = 0,
    var refreshDatabase: Boolean = true
)