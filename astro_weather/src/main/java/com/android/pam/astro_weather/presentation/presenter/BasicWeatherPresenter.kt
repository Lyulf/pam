package com.android.pam.astro_weather.presentation.presenter

import com.android.pam.astro_weather.domain.model.weather.WeatherData
import com.android.pam.astro_weather.domain.usecase.GetSettingsUseCase
import com.android.pam.astro_weather.domain.usecase.GetWeatherDataUseCase
import com.android.pam.astro_weather.presentation.contract.IBasicWeatherContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BasicWeatherPresenter(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val viewModel: IBasicWeatherContract.IViewModel
) : BasePresenter(), IBasicWeatherContract.IPresenter {
    private var view: IBasicWeatherContract.IView? = null
    var cityId: Long? = null

    override suspend fun onAttach(view: IBasicWeatherContract.IView) {
        this.view = view
        view.subscribeWeather()
    }

    override fun onDetach() {
        super.onDetach()
        view?.unsubscribeWeather()
        view = null
        cityId = null
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

    override fun displayWeatherData() = launch {
        loadConfig()
        val weather = cityId?.let {
            withContext(Dispatchers.IO) {
                getWeatherDataUseCase.invoke(it)
            }
        } ?: WeatherData()
       viewModel.setBasicData(weather.basic)
    }

}