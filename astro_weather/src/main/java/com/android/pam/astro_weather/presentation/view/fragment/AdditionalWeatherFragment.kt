package com.android.pam.astro_weather.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.android.pam.astro_weather.R
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.model.weather.AdditionalWeatherData
import com.android.pam.astro_weather.presentation.contract.IAdditionalWeatherContract
import com.android.pam.astro_weather.presentation.contract.IAstroWeatherContract
import kotlinx.android.synthetic.main.fragment_additional_weather.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AdditionalWeatherFragment : BaseFragment(), IAdditionalWeatherContract.IView {
    @Inject lateinit var viewModel: IAdditionalWeatherContract.IViewModel
    @Inject lateinit var presenter: IAdditionalWeatherContract.IPresenter

    private var units: Settings.Units? = null
    private var weather: AdditionalWeatherData? = null

    private val unitsObserver = Observer<Settings.Units> {
        units = it
        updateData()
    }

    private val weatherObserver = Observer<AdditionalWeatherData> {
        weather = it
        updateData()
    }

    override fun onAttach(context: Context) {
        (activity as IAstroWeatherContract.IView).astroWeatherComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_additional_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            presenter.onAttach(this@AdditionalWeatherFragment)
        }
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun subscribeAdditionalWeather() {
        viewModel.additionalData().observe(viewLifecycleOwner, weatherObserver)
        viewModel.units().observe(viewLifecycleOwner, unitsObserver)
    }

    override fun unsubscribeAdditionalWeather() {
        viewModel.additionalData().removeObserver { weatherObserver }
        viewModel.units().removeObserver { unitsObserver }
    }

    private fun updateData() = launch {
        awFragment_txv_humidity?.text = getText(R.string.aw_humidity).toString().format (
            weather?.humidity?.toString() ?: "N/A"
        )
        awFragment_txv_visibility?.text = getText(R.string.aw_visibility).toString().format (
            weather?.visibility?.toString() ?: "N/A"
        )
        awFragment_txv_windDeg?.text = getText(R.string.aw_windDeg).toString().format (
            weather?.wind?.degrees?.toString() ?: "N/A"
        )
        awFragment_txv_windSpeed?.text = getText(R.string.aw_windSpeed).toString().format(
            weather?.wind?.speed?.toString() ?: "N/A"
        )
    }
}