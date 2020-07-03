package com.android.pam.astro_weather.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.android.pam.astro_weather.R
import com.android.pam.astro_weather.domain.model.moon.Moon
import com.android.pam.astro_weather.presentation.contract.IAstroWeatherContract
import com.android.pam.astro_weather.presentation.contract.IMoonContract
import kotlinx.android.synthetic.main.data_moon.*
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class MoonFragment : BaseFragment(), IMoonContract.IView {
    @Inject lateinit var viewModel: IMoonContract.IViewModel
    @Inject lateinit var presenter: IMoonContract.IPresenter

    private var moon: Moon? = null
    private val moonObserver = Observer<Moon> {
        moon = it
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
        return inflater.inflate(R.layout.fragment_moon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            presenter.onAttach(this@MoonFragment)
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

    override fun subscribeMoonModel() {
        viewModel.moon().observe(
            viewLifecycleOwner, moonObserver
        )
    }

    override fun unsubscribeMoonModel() {
        viewModel.moon().removeObserver(moonObserver)
    }

    private fun updateData(moon: Moon) = launch {
        moonFragment_txv_moonrise.text = getString(R.string.moonrise).format(
            moon.moonrise.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
        moonFragment_txv_moonset.text = getString(R.string.moonset).format(
            moon.moonset.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
        moonFragment_txv_fullMoon.text = getString(R.string.fullMoon).format(
            moon.nextFullMoon.date?.format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) ?: "N/A"
        )
        moonFragment_txv_newMoon.text = getString(R.string.newMoon).format(
            moon.nextNewMoon.date?.format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) ?: "N/A"
        )
        moonFragment_txv_moonPhase.text = getString(R.string.moonPhase).format(
            if(moon.moonPhase.percent != null)
                "%.2f".format(moon.moonPhase.percent)
            else
                "N/A"
        )
        moonFragment_txv_daySynodicMonth.text = getString(R.string.dayOfSynodicMonth).format(
            moon.dayOfSynodicMonth?.toString() ?: "N/A"
        )
    }
}
