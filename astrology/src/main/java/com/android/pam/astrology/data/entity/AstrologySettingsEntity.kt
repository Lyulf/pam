package com.android.pam.astrology.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.pam.astrology.domain.model.settings.Location

@Entity(tableName = "astrology_settings_table")
data class AstrologySettingsEntity(
    @ColumnInfo(name = "location")
    val location: Location,
    @ColumnInfo(name = "refresh_frequency")
    val frequency: Double,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0
)