package com.android.pam.astrology.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.pam.astrology.domain.utils.LiveDataUtils.newValue
import com.android.pam.astrology.presentation.contract.ISettingsContract
import com.android.pam.astrology.presentation.model.SettingsModel

class SettingsViewModel : ViewModel(), ISettingsContract.IViewModel {
    private val model by lazy {
        MutableLiveData<SettingsModel>()
    }

    override fun setSettingsModel(settings: SettingsModel) {
        model.newValue = settings
    }
    override fun settingsModel() = model
}