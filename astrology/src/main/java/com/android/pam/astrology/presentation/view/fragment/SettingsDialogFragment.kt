package com.android.pam.astrology.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.android.pam.astrology.R
import com.android.pam.astrology.di.component.DaggerAstrologyComponent
import com.android.pam.astrology.di.module.ActivitiesModule
import com.android.pam.astrology.domain.model.settings.Location
import com.android.pam.astrology.domain.model.settings.Settings
import com.android.pam.astrology.presentation.contract.ISettingsContract
import com.android.pam.astrology.presentation.filter.InputFilterOpenMinMax
import com.android.pam.astrology.presentation.model.SettingsModel
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsDialogFragment : DialogFragment(), ISettingsContract.IView {
    @Inject lateinit var presenter: ISettingsContract.IPresenter
    @Inject lateinit var viewModel: ISettingsContract.IViewModel

    var adapter: ArrayAdapter<String>? = null
    val spinnerValues = arrayListOf(
        1,
        2,
        5,
        10,
        15,
        30,
        60,
        3 * 60,
        12 * 60,
        24 * 60
    ).map { it.toString()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    private val settingsObserver = Observer<SettingsModel> {
        model -> updateSettings(model)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings_btn_confirm.setOnClickListener {
            try {
                val latitude = settings_edt_latitude.text.toString().toDouble()
                val longitude = settings_edt_longitude.text.toString().toDouble()
                val location = Location(latitude, longitude)
                val id = settings_spn_refreshRate.selectedItemPosition
                val refreshRate = spinnerValues[id].toDouble()
                val settings = Settings(location, refreshRate)
                viewModel.setSettingsModel(SettingsModel(settings))
                presenter.onSaveSettings()
                dismiss()
            } catch (e: NumberFormatException) {
                Toast.makeText(context, "All fields need to be filled out.", Toast.LENGTH_SHORT).show()
            }
        }

        settings_spn_refreshRate.adapter = adapter
        settings_edt_latitude.filters = arrayOf(InputFilterOpenMinMax(-90.0, 90.0))
        settings_edt_longitude.filters = arrayOf(InputFilterOpenMinMax(-180.0, 180.0))
        subscribeSettings()
    }

    override fun onAttach(context: Context) {
        DaggerAstrologyComponent.builder()
            .activitiesModule(ActivitiesModule(requireActivity()))
            .build()
            .inject(this)
        super.onAttach(context)
        presenter.onAttach()
        adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, spinnerValues)
    }

    override fun onDetach() {
        unsubscribeSettings()
        adapter = null
        super.onDetach()
    }

    override fun subscribeSettings() {
        viewModel.settingsModel().observe(viewLifecycleOwner, settingsObserver)
        updateSettings(viewModel.settingsModel().value!!)
    }

    override fun unsubscribeSettings() {
        viewModel.settingsModel().removeObserver { settingsObserver }
    }

    private fun updateSettings(model: SettingsModel) {
        settings_edt_latitude.setText(model.settings.location.latitude.toString())
        settings_edt_longitude.setText(model.settings.location.longitude.toString())
        val id = spinnerValues.indexOf(model.settings.astroDataRefreshFrequency.toInt().toString())
        if(id != -1) {
            settings_spn_refreshRate.setSelection(id)
        }
    }

}