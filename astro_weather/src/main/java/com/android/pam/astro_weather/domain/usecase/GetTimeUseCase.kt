package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.wrapper.IDeviceTime
import org.threeten.bp.LocalTime
import javax.inject.Inject

class GetTimeUseCase @Inject constructor(
    private val deviceTime: IDeviceTime
) {
    fun invoke(): LocalTime {
        return deviceTime.currentTime()
    }
}