package com.android.pam.astrology.domain.usecase

import com.android.pam.astrology.domain.model.settings.Settings
import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository
import com.android.pam.astrology.domain.wrapper.IAstrology
import javax.inject.Inject

class SaveAstrologySettingsUseCase @Inject constructor(
    private val repository: IAstrologySettingsRepository,
    private val wrapper: IAstrology
) {
    fun invoke(settings: Settings) {
        repository.setSettings(settings)
        wrapper.updateSettings()
    }
}