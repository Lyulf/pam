package com.android.pam.astro_weather.presentation.contract

import androidx.lifecycle.LiveData
import com.android.pam.astro_weather.dagger.component.SettingsComponent
import com.android.pam.astro_weather.domain.model.location.City
import com.android.pam.astro_weather.domain.model.settings.Settings
import kotlinx.coroutines.Job

interface ISettingsContract {
    interface IView {
        fun settingsComponent(): SettingsComponent

        fun subscribeFavourites()
        fun unsubscribeFavourites()
        fun subscribeSettings()
        fun unsubscribeSettings()
        fun dismiss()
        fun navigateToModifyFavourites()
        fun showSelectLocationMessage()
    }

    interface IViewModel {
        fun settings(): LiveData<Settings>
        fun setSettings(settings: Settings)
        fun favourites(): LiveData<List<City>>
        fun setFavourites(values: List<City>)
    }

    interface IPresenter : IBaseContract.IPresenter {
        suspend fun onAttach(view: IView)

        fun onSaveSettings(): Job
        fun onUpdateData(): Job
        fun onBackPressed(): Job
    }
}