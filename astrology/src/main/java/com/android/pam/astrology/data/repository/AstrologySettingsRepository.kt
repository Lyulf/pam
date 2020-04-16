package com.android.pam.astrology.data.repository

import com.android.pam.astrology.domain.model.settings.Location
import com.android.pam.astrology.domain.model.settings.Settings
import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository

class AstrologySettingsRepository : IAstrologySettingsRepository {
    override fun getSettings(): Settings {
        TODO("Not yet implemented")
    }

    override fun getLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun getRefreshRate(): Int {
        TODO("Not yet implemented")
    }

}