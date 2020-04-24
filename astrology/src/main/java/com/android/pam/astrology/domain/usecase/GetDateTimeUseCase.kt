package com.android.pam.astrology.domain.usecase

import com.android.pam.astrology.domain.wrapper.IDeviceTime
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class GetDateTimeUseCase @Inject constructor(
    private val deviceTime: IDeviceTime
) {
    fun invoke(): LocalDateTime = deviceTime.currentDateTime()
}