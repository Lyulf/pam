package com.android.pam.astro_weather.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.pam.astro_weather.domain.model.moon.Moon
import com.android.pam.astro_weather.domain.utils.LiveDataUtils.newValue
import com.android.pam.astro_weather.presentation.contract.IMoonContract
import com.android.pam.astro_weather.presentation.model.MoonModel

class MoonViewModel : ViewModel(), IMoonContract.IViewModel {
    private val model = MoonModel()

    override fun setMoon(moon: Moon) {
        model.moon.newValue = moon
    }
    override fun moon(): LiveData<Moon> = model.moon
}
