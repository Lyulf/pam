package com.android.pam.astrology.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.pam.astrology.domain.utils.LiveDataUtils.newValue
import com.android.pam.astrology.presentation.contract.IAstrologyContract
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

class AstrologyViewModel : ViewModel(), IAstrologyContract.IViewModel {
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

    override fun nextUpdate(): LocalDateTime? = nextUpdate

}