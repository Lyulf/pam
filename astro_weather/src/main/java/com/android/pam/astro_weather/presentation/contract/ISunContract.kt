package com.android.pam.astro_weather.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astro_weather.domain.model.sun.Sun
import kotlinx.coroutines.Job

interface ISunContract {
    interface IView {
        fun subscribeSunModel()
        fun unsubscribeSunModel()
    }

    interface IViewModel {
        fun sun(): LiveData<Sun>
        fun setSun(sun: Sun)
    }

    interface IPresenter : IBaseContract.IPresenter {
        suspend fun onAttach(view: IView)

        fun onDisplayData(): Job
    }
}