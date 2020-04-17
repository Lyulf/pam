package com.android.pam.astrology.domain.model.sun

import org.threeten.bp.OffsetTime

data class Sunrise(
    val time: OffsetTime = OffsetTime.now(),
    val azimuth: Double = 0.0
)