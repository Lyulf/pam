package com.android.pam.astrology.domain.usecase

import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository
import javax.inject.Inject

class GetDataRefreshRateUseCase @Inject constructor(
    private val repository: IAstrologySettingsRepository
) {
    fun invoke(): Double = repository.getRefreshRate()
}