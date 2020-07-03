package com.android.pam.astro_weather.presentation.presenter

import com.android.pam.astro_weather.domain.usecase.GetMoonDataUseCase
import com.android.pam.astro_weather.domain.usecase.RefreshAstrologicalDataUseCase
import com.android.pam.astro_weather.presentation.contract.IMoonContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoonPresenter @Inject constructor(
    private val refreshAstrologicalDataUseCase: RefreshAstrologicalDataUseCase,
    private val getMoonDataUseCase: GetMoonDataUseCase,
    private val viewModel: IMoonContract.IViewModel?
) : BasePresenter(), IMoonContract.IPresenter {

    private var view: IMoonContract.IView? = null

    override suspend fun onAttach(view: IMoonContract.IView) {
        this.view = view
        view.subscribeMoonModel()
        onDisplayData().join()
    }

    override fun onDetach() {
        view?.unsubscribeMoonModel()
        view = null
        super.onDetach()
    }

    override fun onDisplayData() = launch {
        withContext(Dispatchers.IO) { refreshAstrologicalDataUseCase.invoke() }
        val moon= getMoonDataUseCase.invoke()
        viewModel?.setMoon(moon)
    }

}