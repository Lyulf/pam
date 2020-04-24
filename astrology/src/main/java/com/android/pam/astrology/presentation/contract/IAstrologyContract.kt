package com.android.pam.astrology.presentation.contract

import androidx.lifecycle.LiveData
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

interface IAstrologyContract {
    interface IView {
        fun subscribeTime()
        fun unsubscribeTime()
    }

    interface IViewModel {
        fun setTime(time: LocalTime)
        fun time(): LiveData<LocalTime>
        fun setNextUpdate(nextUpdate: LocalDateTime)
        fun nextUpdate(): LocalDateTime?
    }

    interface IPresenter {
        fun onRefreshTime()
        fun onUpdateData()
        fun startRefreshingTime(frequencyInMs: Long)
        fun stopRefreshingTime()
        fun startUpdatingData()
        fun stopUpdatingData()
    }
}