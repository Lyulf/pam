package com.android.pam.astro_weather.presentation.presenter

import com.android.pam.astro_weather.domain.model.location.City
import com.android.pam.astro_weather.domain.usecase.GetCitiesUseCase
import com.android.pam.astro_weather.domain.usecase.SetCityUseCase
import com.android.pam.astro_weather.presentation.contract.IFavouritesContract
import com.android.pam.astro_weather.presentation.contract.ISettingsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavouritesPresenter @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val setCityUseCase: SetCityUseCase,
    private val settingsPresenter: ISettingsContract.IPresenter,
    private val viewModel: IFavouritesContract.IViewModel
) : BasePresenter(), IFavouritesContract.IPresenter {

    private var view: IFavouritesContract.IView? = null

    override suspend fun onAttach(view: IFavouritesContract.IView) {
        this.view = view
        view.subscribeCities()
    }

    override fun onFinish() {
        settingsPresenter.onUpdateData()
    }

    override fun onDetach() {
        view?.unsubscribeCities()
        view = null
        super.onDetach()
    }

    override suspend fun setCity(city: City) {
        setCityUseCase.invoke(city)
    }

    override fun onFindCities(
        city: String?,
        state: String?,
        country: String?,
        favourite: Boolean?,
        id: Long?
    ) = launch {
            withContext(Dispatchers.IO) {
                getCitiesUseCase.invoke(city, state, country, favourite, id)
            }.let {
                viewModel.setCities(it)
            }.also {
                view?.displaySearchFinished()
            }
        }
}