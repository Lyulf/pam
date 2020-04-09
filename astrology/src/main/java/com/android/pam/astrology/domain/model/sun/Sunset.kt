package com.android.pam.astrology.domain.model.sun

import java.sql.Time

data class Sunset(
    val time: Time,
    val azimuth: Double
)