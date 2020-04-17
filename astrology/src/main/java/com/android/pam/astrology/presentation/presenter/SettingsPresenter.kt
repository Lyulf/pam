package com.android.pam.astrology.presentation.presenter

import com.android.pam.astrology.domain.model.settings.Location
import com.android.pam.astrology.domain.model.settings.Settings
import com.android.pam.astrology.domain.usecase.SaveAstrologySettingsUseCase
import com.android.pam.astrology.presentation.contract.ISettingsContract
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
    private val saveAstrologySettingsUseCase: SaveAstrologySettingsUseCase
) : ISettingsContract.IPresenter {
    override fun onSaveSettings() {
        val settings = Settings(Location(0.0, 0.0), 15.0)
        saveAstrologySettingsUseCase.invoke(settings)
    }

}