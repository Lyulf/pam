package com.android.pam.astrology.domain.wrapper

import org.threeten.bp.LocalTime

interface IDeviceTime {
    fun currentTime(): LocalTime
}