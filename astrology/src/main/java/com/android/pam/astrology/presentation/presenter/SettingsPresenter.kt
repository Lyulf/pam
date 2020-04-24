package com.android.pam.astrology.presentation.presenter

import com.android.pam.astrology.domain.usecase.GetAstrologicalSettingsUseCase
import com.android.pam.astrology.domain.usecase.SaveAstrologySettingsUseCase
import com.android.pam.astrology.presentation.contract.IAstrologyContract
import com.android.pam.astrology.presentation.contract.ISettingsContract
import com.android.pam.astrology.presentation.model.SettingsModel
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
    private val getAstrologySettingsUseCase: GetAstrologicalSettingsUseCase,
    private val saveAstrologySettingsUseCase: SaveAstrologySettingsUseCase,
    private val viewModel: ISettingsContract.IViewModel,
    private val astrologyPresenter: IAstrologyContract.IPresenter
) : ISettingsContract.IPresenter {

    override fun onAttach() {
        val settings = getAstrologySettingsUseCase.invoke()
        val model = SettingsModel(settings)
        viewModel.setSettingsModel(model)
    }

    override fun onSaveSettings() {
        viewModel.settingsModel().value?.let {
            saveAstrologySettingsUseCase.invoke(it.settings)
            astrologyPresenter.onUpdateData()
        }
    }

}