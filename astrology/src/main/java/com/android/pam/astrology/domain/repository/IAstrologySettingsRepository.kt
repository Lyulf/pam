package com.android.pam.astrology.domain.repository

import com.android.pam.astrology.domain.model.settings.Location
import com.android.pam.astrology.domain.model.settings.Settings

interface IAstrologySettingsRepository {
    fun getSettings(): Settings
    fun getLocation(): Location
    fun getRefreshRate(): Int
}