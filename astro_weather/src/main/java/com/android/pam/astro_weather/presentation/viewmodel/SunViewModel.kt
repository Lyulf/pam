package com.android.pam.astro_weather.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.pam.astro_weather.domain.model.sun.Sun
import com.android.pam.astro_weather.domain.utils.LiveDataUtils.newValue
import com.android.pam.astro_weather.presentation.contract.ISunContract
import com.android.pam.astro_weather.presentation.model.SunModel

class SunViewModel : ViewModel(), ISunContract.IViewModel {
    private val model = SunModel()

    override fun setSun(sun: Sun) {
        model.sun.newValue = sun
    }
    override fun sun(): LiveData<Sun> = model.sun
}