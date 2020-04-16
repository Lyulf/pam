package com.android.pam.astrology.domain.model.sun

import org.threeten.bp.OffsetTime

data class Sunset(
    val time: OffsetTime,
    val azimuth: Double
)