package com.android.pam.astro_weather.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.pam.astro_weather.domain.model.location.City
import com.android.pam.astro_weather.domain.utils.LiveDataUtils.newValue
import com.android.pam.astro_weather.presentation.contract.IFavouritesContract
import com.android.pam.astro_weather.presentation.model.FavouritesModel

class FavouritesViewModel : ViewModel(), IFavouritesContract.IViewModel {
    private val model = FavouritesModel()

    override fun cities(): LiveData<List<City>> = model.favouriteCities
    override fun setCities(values: List<City>) {
        model.favouriteCities.newValue = values
    }
}