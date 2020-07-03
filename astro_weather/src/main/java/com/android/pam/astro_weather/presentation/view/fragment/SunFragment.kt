package com.android.pam.astro_weather.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.android.pam.astro_weather.R
import com.android.pam.astro_weather.domain.model.sun.Sun
import com.android.pam.astro_weather.presentation.contract.IAstroWeatherContract
import com.android.pam.astro_weather.presentation.contract.ISunContract
import kotlinx.android.synthetic.main.data_sun.*
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class SunFragment : BaseFragment(), ISunContract.IView {
    @Inject lateinit var presenter: ISunContract.IPresenter
    @Inject lateinit var viewModel: ISunContract.IViewModel

    private var sun: Sun? = null
    private val sunObserver = Observer<Sun> {
        sun = it
        updateData(it)
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
        return inflater.inflate(R.layout.fragment_sun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            presenter.onAttach(this@SunFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onDisplayData()
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun subscribeSunModel() {
        viewModel.sun().observe(viewLifecycleOwner, sunObserver)
    }

    override fun unsubscribeSunModel() {
        viewModel.sun().removeObserver(sunObserver)
    }

    private fun updateData(sun: Sun) = launch {
        sunFragment_txv_sunrise.text = getString(R.string.sunrise).format(
            sun.sunrise.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
        sunFragment_txv_sunset.text = getString(R.string.sunset).format(
            sun.sunset.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
        sunFragment_txv_sunriseAzimuth.text = getString(R.string.sunriseAzimuth).format(
            if(sun.sunrise.azimuth?.isNaN() != false)
                "N/A"
            else
                "%.2f".format(sun.sunrise.azimuth)
        )
        sunFragment_txv_sunsetAzimuth.text = getString(R.string.sunsetAzimuth).format(
            if(sun.sunset.azimuth?.isNaN() != false)
                "N/A"
            else
                "%.2f".format(sun.sunset.azimuth)
        )
        sunFragment_txv_civilDusk.text = getString(R.string.civilDusk).format(
            sun.civilDusk.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
        sunFragment_txv_civilDawn.text = getString(R.string.civilDawn).format(
            sun.civilDawn.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
    }

}
