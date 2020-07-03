package com.android.pam.astro_weather.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astro_weather.domain.model.location.City
import kotlinx.coroutines.Job

interface IFavouritesContract {
    interface IView {
        fun subscribeCities()
        fun unsubscribeCities()
        fun dismiss()

        fun displaySearchFinished()
    }

    interface IViewModel {
        fun cities(): LiveData<List<City>>
        fun setCities(values: List<City>)
    }

    interface IPresenter : IBaseContract.IPresenter {
        suspend fun onAttach(view: IView)
        fun onFinish()

        suspend fun setCity(city: City)
        fun onFindCities(
            city: String? = null,
            state: String? = null,
            country: String? = null,
            favourite: Boolean? = null,
            id: Long? = null
        ): Job
    }
}