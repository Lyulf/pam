package com.android.pam.astrology.data.wrapper

import com.android.pam.astrology.domain.wrapper.IDeviceTime
import org.threeten.bp.LocalTime

class DeviceTimeImpl : IDeviceTime {
    override fun currentTime(): LocalTime {
        return LocalTime.now()
    }

}