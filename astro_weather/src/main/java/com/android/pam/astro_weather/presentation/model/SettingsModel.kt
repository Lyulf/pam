package com.android.pam.astro_weather.presentation.model

import androidx.lifecycle.MutableLiveData
import com.android.pam.astro_weather.domain.model.settings.Settings

class SettingsModel {
    val settings by lazy {
        MutableLiveData<Settings>()
    }
}
