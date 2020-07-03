package com.android.pam.astro_weather.presentation.model

import androidx.lifecycle.MutableLiveData
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.model.weather.AdditionalWeatherData
import com.android.pam.astro_weather.domain.model.weather.BasicWeatherData
import com.android.pam.astro_weather.domain.model.weather.DailyForecast

class WeatherModel {
    val units by lazy {
        MutableLiveData<Settings.Units>()
    }
    val basic by lazy {
        MutableLiveData<BasicWeatherData>()
    }
    val additional by lazy {
        MutableLiveData<AdditionalWeatherData>()
    }
    val forecast by lazy {
        MutableLiveData<List<DailyForecast>>()
    }
}
