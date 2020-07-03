package com.android.pam.astro_weather.data.converter

import androidx.room.TypeConverter
import com.android.pam.astro_weather.data.entity.SettingsEntity
import com.android.pam.astro_weather.domain.model.settings.Settings

object SettingsConverter {
    @TypeConverter
    @JvmStatic
    fun Settings.toSettingsEntity() = SettingsEntity(
            location_id,
            dataRefreshFrequency,
            units.ordinal
        )

    @TypeConverter
    @JvmStatic
    fun SettingsEntity.toSettings() = Settings(
            location_id,
            frequency,
            Settings.Units.values()[units]
    )
}
