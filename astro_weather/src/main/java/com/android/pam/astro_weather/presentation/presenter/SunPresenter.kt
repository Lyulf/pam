package com.android.pam.astro_weather.presentation.presenter

import com.android.pam.astro_weather.domain.usecase.GetSunDataUseCase
import com.android.pam.astro_weather.domain.usecase.RefreshAstrologicalDataUseCase
import com.android.pam.astro_weather.presentation.contract.ISunContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SunPresenter @Inject constructor(
    private val refreshAstrologicalDataUseCase: RefreshAstrologicalDataUseCase,
    private val getSunDataUseCase: GetSunDataUseCase,
    private val viewModel: ISunContract.IViewModel?
) : BasePresenter(), ISunContract.IPresenter {

    private var view: ISunContract.IView? = null

    override suspend fun onAttach(view: ISunContract.IView) {
        this.view = view
        view.subscribeSunModel()
        onDisplayData().join()
    }

    override fun onDetach() {
        view?.unsubscribeSunModel()
        view = null
        super.onDetach()
    }

    override fun onDisplayData() = launch {
        withContext(Dispatchers.IO) { refreshAstrologicalDataUseCase.invoke() }
        val sun = getSunDataUseCase.invoke()
        viewModel?.setSun(sun)
    }

}