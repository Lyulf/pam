package com.android.pam.astro_weather.presentation.model

import androidx.lifecycle.MutableLiveData
import com.android.pam.astro_weather.domain.model.sun.Sun

class SunModel {
    val sun by lazy {
        MutableLiveData<Sun>()
    }
}