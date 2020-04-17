package com.android.pam.astrology.domain.model.sun

data class Sun(
    val sunrise: Sunrise = Sunrise(),
    val sunset: Sunset = Sunset(),
    val civilDusk: Dusk = Dusk(),
    val civilDawn: Dawn = Dawn()
    )
