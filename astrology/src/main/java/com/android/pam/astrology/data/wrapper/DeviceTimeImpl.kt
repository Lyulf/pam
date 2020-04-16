package com.android.pam.astrology.data.wrapper

import com.android.pam.astrology.domain.wrapper.IDeviceTime
import java.sql.Time
import java.util.*

class DeviceTimeImpl : IDeviceTime {
    override fun currentTime(): Time {
        return Time(Calendar.getInstance().timeInMillis)
    }

}