package com.android.pam.astrology.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.pam.astrology.presentation.contract.IAstrologyContract
import org.threeten.bp.LocalTime

class AstrologyViewModel : ViewModel(), IAstrologyContract.IViewModel {
    private val localTime by lazy {
        MutableLiveData<LocalTime>()
    }
    override fun setTime(time: LocalTime) {
        localTime.value = time
    }

    override fun time(): LiveData<LocalTime> = localTime

}