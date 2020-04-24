package com.android.pam.astrology.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.android.pam.astrology.R
import com.android.pam.astrology.di.component.DaggerAstrologyComponent
import com.android.pam.astrology.di.module.ActivitiesModule
import com.android.pam.astrology.presentation.contract.ISunContract
import com.android.pam.astrology.presentation.model.SunModel
import kotlinx.android.synthetic.main.data_sun.*
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class SunFragment : Fragment(), ISunContract.IView {
    @Inject lateinit var presenter: ISunContract.IPresenter
    @Inject lateinit var viewModel: ISunContract.IViewModel

    private val sunObserver = Observer<SunModel> { sunModel ->
        sunFragment_txv_sunrise.text = getString(R.string.sunrise).format(
            sunModel.sun.sunrise.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
        sunFragment_txv_sunset.text = getString(R.string.sunset).format(
            sunModel.sun.sunset.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
        sunFragment_txv_sunriseAzimuth.text = getString(R.string.sunriseAzimuth).format(
            if(sunModel.sun.sunrise.azimuth?.isNaN() != false)
                "N/A"
            else
                "%.2f".format(sunModel.sun.sunrise.azimuth)
        )
        sunFragment_txv_sunsetAzimuth.text = getString(R.string.sunsetAzimuth).format(
            if(sunModel.sun.sunset.azimuth?.isNaN() != false)
                "N/A"
            else
                "%.2f".format(sunModel.sun.sunset.azimuth)
        )
        sunFragment_txv_civilDusk.text = getString(R.string.civilDusk).format(
            sunModel.sun.civilDusk.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
        sunFragment_txv_civilDawn.text = getString(R.string.civilDawn).format(
            sunModel.sun.civilDawn.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
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
        subscribeSunModel()
        presenter.onUpdateData()
    }

    override fun onAttach(context: Context) {
        DaggerAstrologyComponent.builder()
            .activitiesModule(ActivitiesModule(requireActivity()))
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        unsubscribeSunModel()
        super.onDetach()
    }

    override fun subscribeSunModel() {
        viewModel.sunModel().observe(viewLifecycleOwner, sunObserver)
    }

    override fun unsubscribeSunModel() {
        viewModel.sunModel().removeObserver(sunObserver)
    }

}
