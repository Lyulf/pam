package com.android.pam.astro_weather.presentation.contract

interface ISplashContract {
    interface IView {
        fun navigateToAstroWeather()
    }

    interface IController {
        fun onAttach(view: IView)
        fun onDetach()

        fun onStartingApplication()
    }
}