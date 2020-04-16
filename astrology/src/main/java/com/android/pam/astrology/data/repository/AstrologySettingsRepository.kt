package com.android.pam.astrology.data.repository

import com.android.pam.astrology.data.converter.AstrologySettingsConverter
import com.android.pam.astrology.data.dao.AstrologySettingsDao
import com.android.pam.astrology.domain.model.settings.Location
import com.android.pam.astrology.domain.model.settings.Settings
import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository
import javax.inject.Inject

class AstrologySettingsRepository @Inject constructor(
    private val dao: AstrologySettingsDao
) : IAstrologySettingsRepository {

    private val converter = AstrologySettingsConverter()

    override fun setSettings(settings: Settings) {
        val astroSettings =
            converter.settingsToAstrologySettings(settings)
        dao.setSettings(astroSettings)
    }

    override fun getSettings(): Settings {
        return converter.astrologySettingsToSettings(
            dao.getSettings()
        )
    }

    override fun getLocation(): Location {
        return dao.getLocation()
    }

    override fun getRefreshRate(): Double {
        return dao.getRefreshFrequency()
    }

}