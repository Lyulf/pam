package com.android.pam.astro_weather.presentation.model

import androidx.lifecycle.MutableLiveData
import com.android.pam.astro_weather.domain.model.moon.Moon

class MoonModel{
    val moon by lazy {
        MutableLiveData<Moon>()
    }
}
