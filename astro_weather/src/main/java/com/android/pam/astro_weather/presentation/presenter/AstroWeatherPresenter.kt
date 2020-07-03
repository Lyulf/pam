package com.android.pam.astro_weather.presentation.presenter

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.FragmentActivity
import com.android.pam.astro_weather.domain.usecase.*
import com.android.pam.astro_weather.presentation.contract.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZoneId
import org.threeten.bp.temporal.ChronoUnit
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AstroWeatherPresenter @Inject constructor(
    private val getTimeUseCase: GetTimeUseCase,
    private val getDateTimeUseCase: GetDateTimeUseCase,
    private val getDataRefreshRateUseCase: GetDataRefreshRateUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val downloadWeatherDataUseCase: DownloadWeatherDataUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val viewModel: IAstroWeatherContract.IViewModel,
    private val sunPresenter: ISunContract.IPresenter,
    private val moonPresenter: IMoonContract.IPresenter,
    private val basicWeatherPresenter: IBasicWeatherContract.IPresenter,
    private val additionalWeatherPresenter: IAdditionalWeatherContract.IPresenter,
    private val forecastPresenter: IForecastContract.IPresenter
) : BasePresenter(), IAstroWeatherContract.IPresenter {
    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(3)
    private var refreshTimeFuture: ScheduledFuture<*>? = null
    private var updateAstrologyDataFuture: ScheduledFuture<*>? = null
    private var updateWeatherDataFuture: ScheduledFuture<*>? = null
    private var view: IAstroWeatherContract.IView? = null

    override suspend fun onAttach(view: IAstroWeatherContract.IView) {
        this.view = view
        val settings = withContext(Dispatchers.IO) { getSettingsUseCase.invoke() }

        if(settings.location_id == null) {
            view.navigateToSettings()
        }
        onUpdateAstrologyData()
    }

    override fun onDetach() {
        view = null
        super.onDetach()
    }

    override fun onResume() = launch {
        startRefreshingTime(1000)
        startUpdatingAstrologyData()
        startUpdatingWeatherData()
    }

    override fun onPause() = launch {
        stopUpdatingAstrologyData()
        stopUpdatingWeatherData()
        stopRefreshingTime()
    }

    override suspend fun onUpdate() {
        onUpdateWeatherData()
        onUpdateAstrologyData()
    }

    override fun onRefreshTime() = launch {
        val time = getTimeUseCase.invoke()
        viewModel.setTime(time)
    }

    override fun onUpdateAstrologyData() = launch {
        val sunJob = sunPresenter.onDisplayData()
        val moonJob = moonPresenter.onDisplayData()
        sunJob.join()
        moonJob.join()
    }

    private fun startRefreshingTime(frequencyInMs: Long) {
        refreshTimeFuture = scheduler.scheduleAtFixedRate({
            onRefreshTime()
        },
        0L,
        frequencyInMs,
        TimeUnit.MILLISECONDS)
    }

    private fun stopRefreshingTime() {
        refreshTimeFuture?.cancel(false)
    }

    private fun startUpdatingAstrologyData() = launch {
        val currentDateTime = getDateTimeUseCase.invoke()
        if(viewModel.nextAstrologyUpdate() == null) {
            viewModel.setNextUpdate(currentDateTime)
        }
        val initialDelay = currentDateTime.until(viewModel.nextAstrologyUpdate(), ChronoUnit.MINUTES)
        updateAstrologyDataFuture = scheduler.scheduleAtFixedRate({
            scheduleAstrologyData()
        },
            if(initialDelay > 0L) initialDelay else 0L,
            withContext(Dispatchers.IO) { getDataRefreshRateUseCase.invoke().toLong() },
            TimeUnit.MINUTES
        )
    }

    private fun scheduleAstrologyData() = launch {
        onUpdateAstrologyData()
        val currentDateTime = getDateTimeUseCase.invoke()
        val refreshRate = withContext(Dispatchers.IO) { getDataRefreshRateUseCase.invoke() }
        val nextUpdate = currentDateTime.plusMinutes(refreshRate.toLong())
        viewModel.setNextUpdate(nextUpdate)

    }

    private fun stopUpdatingAstrologyData() {
        updateAstrologyDataFuture?.cancel(false)
    }

    private fun startUpdatingWeatherData() = launch {
        val currentDateTime = getDateTimeUseCase.invoke().atZone(ZoneId.systemDefault())
        val settings = withContext(Dispatchers.IO) { getSettingsUseCase.invoke() }
        val refreshRate = withContext(Dispatchers.IO) { getDataRefreshRateUseCase.invoke() }
        val lastUpdate = settings.location_id?.let {
            withContext(Dispatchers.IO) {
                getWeatherDataUseCase.invoke(it)
            }?.basic?.time
        } ?: currentDateTime.minusMinutes(refreshRate.toLong())

        val initialDelay = refreshRate.toLong() - lastUpdate.until(currentDateTime, ChronoUnit.MINUTES)

        updateWeatherDataFuture = scheduler.scheduleAtFixedRate({
            scheduleWeatherData()
        },
            if(initialDelay > 0L) initialDelay else 0L,
            refreshRate.toLong(),
            TimeUnit.MINUTES
        )

    }

    private fun scheduleWeatherData() = launch {
            onUpdateWeatherData()
    }

    private fun onUpdateWeatherData() = launch {
        withContext(Dispatchers.IO) {
            getSettingsUseCase.invoke()
        }.location_id?.let {
            if(downloadWeather(it)) {
                basicWeatherPresenter.displayWeatherData()
                additionalWeatherPresenter.displayWeatherData()
                forecastPresenter.displayWeatherData()
                view?.hideDownloadError()
                view?.showDataRefreshedMessage()
            } else {
                val time = withContext(Dispatchers.IO) { getWeatherDataUseCase.invoke(it) }?.basic?.time
                view?.showDownloadError(time?.toLocalDateTime())
            }
        }
    }

    private suspend fun downloadWeather(locationId: Long): Boolean {
        val cm = (view as FragmentActivity).getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return if(networkInfo?.isConnected == true) {
            withContext(Dispatchers.IO) {
                downloadWeatherDataUseCase.invoke(locationId)
            }
        } else false

    }

    private fun stopUpdatingWeatherData() {
        updateWeatherDataFuture?.cancel(false)
    }
}