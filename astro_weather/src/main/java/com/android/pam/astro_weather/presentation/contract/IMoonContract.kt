package com.android.pam.astro_weather.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astro_weather.domain.model.moon.Moon
import kotlinx.coroutines.Job

interface IMoonContract {
    interface IView {
        fun subscribeMoonModel()
        fun unsubscribeMoonModel()
    }

    interface IViewModel {
        fun moon(): LiveData<Moon>
        fun setMoon(moon: Moon)
    }

    interface IPresenter : IBaseContract.IPresenter {
        suspend fun onAttach(view: IView)

        fun onDisplayData(): Job
    }
}