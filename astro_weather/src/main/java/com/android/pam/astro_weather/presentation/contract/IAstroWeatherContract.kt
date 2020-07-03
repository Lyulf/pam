package com.android.pam.astro_weather.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astro_weather.dagger.component.AstroWeatherComponent
import kotlinx.coroutines.Job
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

interface IAstroWeatherContract {
    interface IView {
        fun astroWeatherComponent(): AstroWeatherComponent
        fun navigateToSettings()
        fun showDownloadError(lastUpdate: LocalDateTime?)
        fun hideDownloadError()

        fun subscribeTime()
        fun unsubscribeTime()
        fun showDataRefreshedMessage()
    }

    interface IViewModel {
        fun setTime(time: LocalTime)
        fun time(): LiveData<LocalTime>
        fun setNextUpdate(nextUpdate: LocalDateTime)
        fun nextAstrologyUpdate(): LocalDateTime?
    }

    interface IPresenter : IBaseContract.IPresenter {
        suspend fun onAttach(view: IView)
        fun onResume(): Job
        fun onPause(): Job

        suspend fun onUpdate()
        fun onRefreshTime(): Job
        fun onUpdateAstrologyData(): Job
    }
}