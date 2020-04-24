package com.android.pam.astrology.domain.model.settings

data class Settings(
    val location: Location = Location(),
    val astroDataRefreshFrequency: Double = 1.0
)