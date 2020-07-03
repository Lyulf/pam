package com.android.pam.astro_weather.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.pam.astro_weather.domain.model.location.City
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.utils.LiveDataUtils.newValue
import com.android.pam.astro_weather.presentation.contract.ISettingsContract
import com.android.pam.astro_weather.presentation.model.SettingsModel

class SettingsViewModel : ViewModel(), ISettingsContract.IViewModel {
    private val model = SettingsModel()
    private val locations by lazy {
        MutableLiveData<List<City>>()
    }

    override fun setSettings(settings: Settings) {
        model.settings.newValue = settings
    }
    override fun settings() = model.settings

    override fun setFavourites(values: List<City>) {
        locations.newValue = values
    }

    override fun favourites() = locations
}