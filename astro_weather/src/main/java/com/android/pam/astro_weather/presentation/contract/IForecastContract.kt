package com.android.pam.astro_weather.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.model.weather.DailyForecast
import kotlinx.coroutines.Job

interface IForecastContract {
    interface IView {
        fun subscribeForecasts()
        fun unsubscribeForecasts()
    }

    interface IViewModel {
        fun units() : LiveData<Settings.Units>
        fun setUnits(units: Settings.Units)

        fun dailyForecast(): LiveData<List<DailyForecast>>
        fun setDailyForecast(forecast: List<DailyForecast>?)
    }

    interface IPresenter : IBaseContract.IPresenter {
        suspend fun onAttach(view: IView)
        fun displayWeatherData()
        fun onResume(): Job
    }
}