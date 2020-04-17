package com.android.pam.astrology.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astrology.presentation.model.SettingsModel

interface ISettingsContract {
    interface IView {
    }

    interface IViewModel {
        fun settingsModel(): LiveData<SettingsModel>
        fun setSettingsModel(settings: SettingsModel)
    }

    interface IPresenter {
        fun onSaveSettings()
    }
}