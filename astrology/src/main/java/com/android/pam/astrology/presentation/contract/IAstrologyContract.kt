package com.android.pam.astrology.presentation.contract

import androidx.lifecycle.LiveData
import org.threeten.bp.LocalTime

interface IAstrologyContract {
    interface IView {
        fun subscribeTime()
        fun unsubscribeTime()
    }

    interface IViewModel {
        fun setTime(time: LocalTime)
        fun time(): LiveData<LocalTime>
    }

    interface IPresenter {
        fun refreshTime()

    }
}