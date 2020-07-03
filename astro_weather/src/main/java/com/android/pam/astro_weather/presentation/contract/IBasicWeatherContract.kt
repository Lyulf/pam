package com.android.pam.astro_weather.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.model.weather.BasicWeatherData
import kotlinx.coroutines.Job

interface IBasicWeatherContract {
    interface IView {
        fun subscribeWeather()
        fun unsubscribeWeather()
    }

    interface IViewModel {
        fun units() : LiveData<Settings.Units>
        fun setUnits(units: Settings.Units)

        fun basicData(): LiveData<BasicWeatherData>
        fun setBasicData(data: BasicWeatherData?)
    }
    interface IPresenter : IBaseContract.IPresenter {
        suspend fun onAttach(view: IView)
        fun displayWeatherData(): Job
        fun onResume(): Job
    }
}
