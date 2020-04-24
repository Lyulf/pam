package com.android.pam.astrology.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astrology.presentation.model.SettingsModel

interface ISettingsContract {
    interface IView {
        fun subscribeSettings()
        fun unsubscribeSettings()
        fun dismiss()
    }

    interface IViewModel {
        fun settingsModel(): LiveData<SettingsModel>
        fun setSettingsModel(settings: SettingsModel)
    }

    interface IPresenter {
        fun onAttach()
        fun onSaveSettings()
    }
}