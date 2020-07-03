package com.android.pam.astro_weather.presentation.model

import androidx.lifecycle.MutableLiveData
import com.android.pam.astro_weather.domain.model.location.City

class FavouritesModel {
    val favouriteCities by lazy {
        MutableLiveData<List<City>>()
    }
}
