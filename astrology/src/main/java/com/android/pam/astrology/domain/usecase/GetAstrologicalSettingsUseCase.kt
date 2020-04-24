package com.android.pam.astrology.domain.usecase

import com.android.pam.astrology.domain.model.settings.Settings
import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository

class GetAstrologicalSettingsUseCase(
    private val repository: IAstrologySettingsRepository
) {
    fun invoke(): Settings {
        return repository.getSettings()
    }
}