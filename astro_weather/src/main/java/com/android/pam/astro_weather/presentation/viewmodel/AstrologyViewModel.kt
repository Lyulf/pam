package com.android.pam.astro_weather.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.pam.astro_weather.domain.utils.LiveDataUtils.newValue
import com.android.pam.astro_weather.presentation.contract.IAstroWeatherContract
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

class AstrologyViewModel : ViewModel(), IAstroWeatherContract.IViewModel {
    private val localTime by lazy {
        MutableLiveData<LocalTime>()
    }
    private var nextUpdate: LocalDateTime? = null

    override fun setTime(time: LocalTime) {
        localTime.newValue = time
    }

    override fun time(): LiveData<LocalTime> = localTime

    override fun setNextUpdate(nextUpdate: LocalDateTime) {
        this.nextUpdate = nextUpdate
    }

    override fun nextAstrologyUpdate(): LocalDateTime? = nextUpdate

}