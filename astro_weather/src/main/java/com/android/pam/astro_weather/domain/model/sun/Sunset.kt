package com.android.pam.astro_weather.domain.model.sun

import org.threeten.bp.OffsetTime

data class Sunset(
    val time: OffsetTime? = null,
    val azimuth: Double? = null
)