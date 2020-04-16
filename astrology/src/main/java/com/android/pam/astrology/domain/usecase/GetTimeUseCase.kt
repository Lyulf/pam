package com.android.pam.astrology.domain.usecase

import com.android.pam.astrology.domain.wrapper.IDeviceTime
import org.threeten.bp.LocalTime
import javax.inject.Inject

class GetTimeUseCase @Inject constructor(
    private val deviceTime: IDeviceTime
) {
    fun invoke(): LocalTime {
        return deviceTime.currentTime()
    }
}