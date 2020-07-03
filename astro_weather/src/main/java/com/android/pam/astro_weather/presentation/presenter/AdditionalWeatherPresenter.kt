package com.android.pam.astro_weather.presentation.presenter

import com.android.pam.astro_weather.domain.model.weather.WeatherData
import com.android.pam.astro_weather.domain.usecase.GetSettingsUseCase
import com.android.pam.astro_weather.domain.usecase.GetWeatherDataUseCase
import com.android.pam.astro_weather.presentation.contract.IAdditionalWeatherContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdditionalWeatherPresenter @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val viewModel: IAdditionalWeatherContract.IViewModel
) : BasePresenter(), IAdditionalWeatherContract.IPresenter {
    private var view: IAdditionalWeatherContract.IView? = null
    private var cityId: Long? = null

    override suspend fun onAttach(view: IAdditionalWeatherContract.IView) {
        this.view = view
        view.subscribeAdditionalWeather()
    }

    override fun onDetach() {
        view?.unsubscribeAdditionalWeather()
        view = null
        super.onDetach()
    }

    override fun onResume() = launch {
        displayWeatherData()
    }

    private suspend fun loadConfig() {
        withContext(Dispatchers.IO) {
            getSettingsUseCase.invoke()
        }.let {
            cityId = it.location_id
            viewModel.setUnits(it.units)
        }
    }

    override fun displayWeatherData() = launch {
        loadConfig()
        val weather = cityId?.let {
            withContext(Dispatchers.IO) {
                getWeatherDataUseCase.invoke(it)
            }
        } ?: WeatherData()
        viewModel.setAdditionalData(weather.additional)
    }

}