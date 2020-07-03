package com.android.pam.astro_weather.presentation.presenter

import com.android.pam.astro_weather.domain.model.weather.WeatherData
import com.android.pam.astro_weather.domain.usecase.GetSettingsUseCase
import com.android.pam.astro_weather.domain.usecase.GetWeatherDataUseCase
import com.android.pam.astro_weather.presentation.contract.IForecastContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastPresenter @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val viewModel: IForecastContract.IViewModel
) : BasePresenter(), IForecastContract.IPresenter {
    private var view: IForecastContract.IView? = null
    private var cityId: Long? = null

    override suspend fun onAttach(view: IForecastContract.IView) {
        this.view = view
        view.subscribeForecasts()
    }

    override fun onResume() = launch {
        displayWeatherData()
    }

    private suspend fun loadConfig() {
        val settings = withContext(Dispatchers.IO) {
            getSettingsUseCase.invoke()
        }
        cityId = settings.location_id
        viewModel.setUnits(settings.units)
    }

    override fun onDetach() {
        super.onDetach()
        view?.unsubscribeForecasts()
        view = null
    }

    override fun displayWeatherData() {
        launch {
            loadConfig()
            val weather = cityId?.let {
                withContext(Dispatchers.IO) {
                    getWeatherDataUseCase.invoke(it)
                }
            } ?: WeatherData()
            viewModel.setDailyForecast(weather.forecast)
        }
    }

}