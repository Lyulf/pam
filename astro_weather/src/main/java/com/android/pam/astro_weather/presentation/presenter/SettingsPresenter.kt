package com.android.pam.astro_weather.presentation.presenter

import com.android.pam.astro_weather.domain.usecase.GetFavouriteCitiesUseCase
import com.android.pam.astro_weather.domain.usecase.GetSettingsUseCase
import com.android.pam.astro_weather.domain.usecase.SaveSettingsUseCase
import com.android.pam.astro_weather.presentation.contract.ISettingsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
    private val getAstrologySettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val getFavouriteCitiesUseCase: GetFavouriteCitiesUseCase,
    private val viewModel: ISettingsContract.IViewModel
) : BasePresenter(), ISettingsContract.IPresenter {

    private var view: ISettingsContract.IView? = null

    override suspend fun onAttach(view: ISettingsContract.IView) {
        this.view = view
        view.subscribeSettings()
        view.subscribeFavourites()
        onUpdateData().join()
    }

    override fun onDetach() {
        view?.unsubscribeSettings()
        view?.unsubscribeFavourites()
        view = null
        super.onDetach()
    }

    override fun onUpdateData() = launch {
        val cities = async(Dispatchers.IO) {
            getFavouriteCitiesUseCase.invoke()
        }
        val settings = async(Dispatchers.IO) {
            getAstrologySettingsUseCase.invoke()
        }
        viewModel.setFavourites(
            cities.await()
        )
        viewModel.setSettings(settings.await())
    }

    override fun onBackPressed() = launch {
        withContext(Dispatchers.IO) { getAstrologySettingsUseCase.invoke() }.location_id?.let {
            view?.dismiss()
        } ?: view?.showSelectLocationMessage()
    }

    override fun onSaveSettings() = launch {
        val settings = viewModel.settings().value
        withContext(Dispatchers.IO) {
            settings?.let {
                saveSettingsUseCase.invoke(it)
            }
        }
    }
}
