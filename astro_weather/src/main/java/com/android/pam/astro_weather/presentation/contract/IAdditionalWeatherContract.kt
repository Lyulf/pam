package com.android.pam.astro_weather.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.model.weather.AdditionalWeatherData
import kotlinx.coroutines.Job


interface IAdditionalWeatherContract {
    interface IView {
        fun subscribeAdditionalWeather()
        fun unsubscribeAdditionalWeather()
    }

    interface IViewModel {
        fun units() : LiveData<Settings.Units>
        fun setUnits(units: Settings.Units)

        fun additionalData(): LiveData<AdditionalWeatherData>
        fun setAdditionalData(data: AdditionalWeatherData?)
    }
    interface IPresenter : IBaseContract.IPresenter {
        suspend fun onAttach(view: IView)
        fun displayWeatherData(): Job
        fun onResume(): Job
    }
}