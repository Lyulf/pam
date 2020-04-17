package com.android.pam.astrology.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astrology.presentation.model.MoonModel

interface IMoonContract {
    interface IView {
        fun subscribeMoonModel()
        fun unsubscribeMoonModel()
    }

    interface IViewModel {
        fun moonModel(): LiveData<MoonModel>
        fun setMoonModel(moonModel: MoonModel)
    }

    interface IPresenter {
        fun onUpdateData()

    }
}