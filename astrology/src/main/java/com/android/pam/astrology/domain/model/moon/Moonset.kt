package com.android.pam.astrology.domain.model.moon

import org.threeten.bp.OffsetTime

data class Moonset(
    val time: OffsetTime = OffsetTime.now()
)