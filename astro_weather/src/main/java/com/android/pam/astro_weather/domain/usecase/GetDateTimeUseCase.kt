package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.wrapper.IDeviceTime
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class GetDateTimeUseCase @Inject constructor(
    private val deviceTime: IDeviceTime
) {
    fun invoke(): LocalDateTime = deviceTime.currentDateTime()
}