package com.android.pam.astro_weather.presentation.controller

import com.android.pam.astro_weather.domain.usecase.InitDatabaseUseCase
import com.android.pam.astro_weather.presentation.contract.ISplashContract
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SplashController @Inject constructor(
    private val initDatabaseUseCase: InitDatabaseUseCase
): ISplashContract.IController, CoroutineScope {

    private var view: ISplashContract.IView? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onAttach(view: ISplashContract.IView) {
        this.view = view
    }

    override fun onDetach() {
        job.cancel()
        view = null
    }

    override fun onStartingApplication() {
        launch {
            withContext(Dispatchers.IO) {
                initDatabaseUseCase.invoke()
            }
            view?.navigateToAstroWeather()
        }
    }
}