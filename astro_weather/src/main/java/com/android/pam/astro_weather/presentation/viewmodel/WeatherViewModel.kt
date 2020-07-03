package com.android.pam.astro_weather.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.model.weather.AdditionalWeatherData
import com.android.pam.astro_weather.domain.model.weather.BasicWeatherData
import com.android.pam.astro_weather.domain.model.weather.DailyForecast
import com.android.pam.astro_weather.domain.utils.LiveDataUtils.newValue
import com.android.pam.astro_weather.presentation.contract.IAdditionalWeatherContract
import com.android.pam.astro_weather.presentation.contract.IBasicWeatherContract
import com.android.pam.astro_weather.presentation.contract.IForecastContract
import com.android.pam.astro_weather.presentation.model.WeatherModel

class WeatherViewModel :
    ViewModel(),
    IBasicWeatherContract.IViewModel,
    IAdditionalWeatherContract.IViewModel,
    IForecastContract.IViewModel {

    private val weather = WeatherModel()

    override fun units(): LiveData<Settings.Units> {
        return weather.units
    }

    override fun setUnits(units: Settings.Units) {
        weather.units.newValue = units
    }

    override fun basicData(): LiveData<BasicWeatherData> {
        return weather.basic
    }

    override fun setBasicData(data: BasicWeatherData?) {
        weather.basic.newValue = data
    }

    override fun additionalData(): LiveData<AdditionalWeatherData> {
        return weather.additional
    }

    override fun setAdditionalData(data: AdditionalWeatherData?) {
        weather.additional.newValue = data
    }

    override fun dailyForecast(): LiveData<List<DailyForecast>> {
        return weather.forecast
    }

    override fun setDailyForecast(forecast: List<DailyForecast>?) {
        weather.forecast.newValue = forecast
    }
}