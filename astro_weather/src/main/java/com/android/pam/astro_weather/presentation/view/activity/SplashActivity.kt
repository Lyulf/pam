package com.android.pam.astro_weather.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import com.android.pam.astro_weather.AstroWeatherApp
import com.android.pam.astro_weather.dagger.component.DaggerSplashComponent
import com.android.pam.astro_weather.dagger.component.SplashComponent
import com.android.pam.astro_weather.presentation.contract.ISplashContract
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashActivity: BaseActivity(), ISplashContract.IView {
    @Inject lateinit var controller: ISplashContract.IController

    private lateinit var component: SplashComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        component = DaggerSplashComponent.builder()
            .applicationComponent((application as AstroWeatherApp).appComponent())
//            .activitiesModule(ActivitiesModule(this))
            .build()
        component.inject(this)
        super.onCreate(savedInstanceState)

        controller.onAttach(this)
        launch {
            controller.onStartingApplication()
        }
    }

    override fun navigateToAstroWeather() {
        val intent = Intent(applicationContext, AstroWeatherActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}