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
import com.android.pam.astrology.presentation.contract.IMoonContract
import com.android.pam.astrology.presentation.model.MoonModel
import kotlinx.android.synthetic.main.data_moon.*
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class MoonFragment : Fragment(), IMoonContract.IView {
    @Inject lateinit var viewModel: IMoonContract.IViewModel
    @Inject lateinit var presenter: IMoonContract.IPresenter

    private val moonObserver = Observer<MoonModel> { moonModel ->
        moonFragment_txv_moonrise.text = getString(R.string.moonrise).format(
            moonModel.moon.moonrise.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
        moonFragment_txv_moonset.text = getString(R.string.moonset).format(
            moonModel.moon.moonset.time?.format(DateTimeFormatter.ISO_LOCAL_TIME) ?: "N/A"
        )
        moonFragment_txv_fullMoon.text = getString(R.string.fullMoon).format(
            moonModel.moon.nextFullMoon.date?.format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) ?: "N/A"
        )
        moonFragment_txv_newMoon.text = getString(R.string.newMoon).format(
            moonModel.moon.nextNewMoon.date?.format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) ?: "N/A"
        )
        moonFragment_txv_moonPhase.text = getString(R.string.moonPhase).format(
            if(moonModel.moon.moonPhase.percent != null)
                "%.2f".format(moonModel.moon.moonPhase.percent)
            else
                "N/A"
        )
        moonFragment_txv_daySynodicMonth.text = getString(R.string.dayOfSynodicMonth).format(
            moonModel.moon.dayOfSynodicMonth?.toString() ?: "N/A"
        )
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
        subscribeMoonModel()
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
        unsubscribeMoonModel()
        super.onDetach()
    }

    override fun subscribeMoonModel() {
        viewModel.moonModel().observe(
            viewLifecycleOwner, moonObserver
        )
    }

    override fun unsubscribeMoonModel() {
        viewModel.moonModel().removeObserver(moonObserver)
    }
}
