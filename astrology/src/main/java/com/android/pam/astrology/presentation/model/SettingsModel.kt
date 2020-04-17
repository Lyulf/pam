package com.android.pam.astrology.presentation.model

import com.android.pam.astrology.domain.model.settings.Settings

data class SettingsModel(
    val settings: Settings = Settings()
)