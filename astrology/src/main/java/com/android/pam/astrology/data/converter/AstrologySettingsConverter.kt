package com.android.pam.astrology.data.converter

import androidx.room.TypeConverter
import com.android.pam.astrology.data.entity.AstrologySettingsEntity
import com.android.pam.astrology.domain.model.settings.Settings

class AstrologySettingsConverter {
    @TypeConverter
    fun settingsToAstrologySettings(settings: Settings): AstrologySettingsEntity {
        return AstrologySettingsEntity(
            settings.location,
            settings.astroDataRefreshFrequency
        )
    }

    @TypeConverter
    fun astrologySettingsToSettings(settings: AstrologySettingsEntity): Settings {
        return Settings(
            settings.location,
            settings.frequency
        )
    }
}