package com.android.pam.astrology.domain.wrapper

import java.sql.Time

interface IDeviceTime {
    fun currentTime(): Time
}