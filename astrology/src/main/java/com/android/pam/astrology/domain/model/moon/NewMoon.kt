package com.android.pam.astrology.domain.model.moon

import org.threeten.bp.ZonedDateTime

data class NewMoon(
    val date: ZonedDateTime = ZonedDateTime.now()
)