package com.android.pam.astro_weather.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.android.pam.astro_weather.R
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.model.weather.BasicWeatherData
import com.android.pam.astro_weather.presentation.contract.IAstroWeatherContract
import com.android.pam.astro_weather.presentation.contract.IBasicWeatherContract
import com.android.pam.astro_weather.presentation.converter.UnitsConverter
import com.android.pam.astro_weather.presentation.extensions.ImageViewExtensions.setImage
import kotlinx.android.synthetic.main.fragment_basic_weather.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class BasicWeatherFragment : BaseFragment(), IBasicWeatherContract.IView {
    @Inject lateinit var viewModel: IBasicWeatherContract.IViewModel
    @Inject lateinit var presenter: IBasicWeatherContract.IPresenter

    private var units: Settings.Units? = null
    private var weather: BasicWeatherData? = null

    private val unitsObserver = Observer<Settings.Units> {
        updateData()
    }

    private val weatherObserver = Observer<BasicWeatherData> {
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
        return inflater.inflate(R.layout.fragment_basic_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            presenter.onAttach(this@BasicWeatherFragment)
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

    override fun subscribeWeather() {
        launch {
            viewModel.units().observe(viewLifecycleOwner, unitsObserver)
            viewModel.basicData().observe(viewLifecycleOwner, weatherObserver)
        }
    }

    override fun unsubscribeWeather() {
        launch {
            viewModel.units().removeObserver { unitsObserver }
            viewModel.basicData().removeObserver { weatherObserver }
        }
    }

    private fun updateData() {
        launch {
            val converter = units?.let { UnitsConverter(it) }
            basicWeatherFragment_txv_location?.text = getText(R.string.bw_location).toString().format(
                weather?.location ?: "N/A"
            )
            basicWeatherFragment_txv_latitude?.text = getText(R.string.bw_latitude).toString().format(
                weather?.latitude?.toString() ?: "N/A"
            )
            basicWeatherFragment_txv_longitude?.text = getText(R.string.bw_longitude).toString().format(
                weather?.longitude?.toString() ?: "N/A"
            )
            basicWeatherFragment_txv_pressure?.text = getText(R.string.bw_pressure).toString().format(
                weather?.pressure?.toString() ?: "N/A"
            )
            basicWeatherFragment_txv_temperature?.text = getText(R.string.bw_temperature).toString().format(
                converter?.temperature(weather?.temperature) ?: "N/A"
            )
            basicWeatherFragment_txv_description?.text = getText(R.string.bw_description).toString().format(
                weather?.description ?: "N/A"
            )
            context?.let {
                if(weather != null) {
                    basicWeatherFragment_imgv_icon.setImage("ic_${weather?.icon}", "drawable", it.packageName)
                    basicWeatherFragment_imgv_icon.visibility = VISIBLE
                } else {
                    basicWeatherFragment_imgv_icon.visibility = GONE
                }
            }
        }
    }
}
