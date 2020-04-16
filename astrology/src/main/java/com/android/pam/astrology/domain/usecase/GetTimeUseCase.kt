package com.android.pam.astrology.domain.usecase

import com.android.pam.astrology.domain.wrapper.IDeviceTime
import java.sql.Time
import javax.inject.Inject

class GetTimeUseCase @Inject constructor(
    private val deviceTime: IDeviceTime
) {
    fun invoke(): Time {
        return deviceTime.currentTime()
    }
}