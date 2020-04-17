package com.android.pam.astrology.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astrology.presentation.model.SunModel

interface ISunContract {
    interface IView {
        fun subscribeSunModel()
        fun unsubscribeSunModel()
    }

    interface IViewModel {
        fun sunModel(): LiveData<SunModel>
        fun setSunModel(sunModel: SunModel)
    }

    interface IPresenter {
        fun onUpdateData()
    }
}