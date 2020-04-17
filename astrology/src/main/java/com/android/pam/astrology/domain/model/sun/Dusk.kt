package com.android.pam.astrology.domain.model.sun

import org.threeten.bp.OffsetTime

data class Dusk(
    val time: OffsetTime = OffsetTime.now()
)