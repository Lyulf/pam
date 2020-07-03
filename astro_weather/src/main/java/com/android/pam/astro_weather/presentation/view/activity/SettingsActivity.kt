package com.android.pam.astro_weather.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import com.android.pam.astro_weather.AstroWeatherApp
import com.android.pam.astro_weather.R
import com.android.pam.astro_weather.dagger.component.DaggerSettingsComponent
import com.android.pam.astro_weather.dagger.component.SettingsComponent
import com.android.pam.astro_weather.dagger.module.ActivitiesModule
import com.android.pam.astro_weather.domain.model.location.City
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.presentation.contract.ISettingsContract
import com.android.pam.astro_weather.presentation.view.fragment.ModifyFavouritesDialogFragment
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsActivity : BaseActivity(), ISettingsContract.IView {
    @Inject lateinit var presenter: ISettingsContract.IPresenter
    @Inject lateinit var viewModel: ISettingsContract.IViewModel

    private var locationAdapter: ArrayAdapter<String>? = null
    private var refreshRateAdapter: ArrayAdapter<String>? = null
    private var unitsAdapter: ArrayAdapter<String>? = null
    private val refreshRateSpinnerValues = arrayListOf(
        10,
        30,
        60,
        3 * 60,
        12 * 60,
        24 * 60
    ).map { it.toString()}
    private val unitsSpinnerValues = Settings.Units.values().map { it.name }

    private var locations: List<City> = listOf()
    private val locationsObserver = Observer<List<City>> {
        locations = it
        updateLocations()
    }

    private lateinit var component: SettingsComponent

    override fun settingsComponent() = component

    private var settings: Settings? = null
    private val settingsObserver = Observer<Settings> {
        settings = it
        updateSettings()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component = DaggerSettingsComponent.builder()
            .applicationComponent((application as AstroWeatherApp).appComponent())
            .activitiesModule(ActivitiesModule(this))
            .build()
        component.inject(this)
        super.onCreate(savedInstanceState)

        launch {
            setContentView(R.layout.activity_settings)

            refreshRateAdapter = ArrayAdapter(
                this@SettingsActivity,
                R.layout.support_simple_spinner_dropdown_item,
                refreshRateSpinnerValues
            )
            unitsAdapter = ArrayAdapter(
                this@SettingsActivity,
                R.layout.support_simple_spinner_dropdown_item,
                unitsSpinnerValues
            )

            settingsFragment_spn_refreshRate.adapter = refreshRateAdapter
            settingsFragment_spn_units.adapter = unitsAdapter

            presenter.onAttach(this@SettingsActivity)

            settingsFragment_btn_modifyFavourites.setOnClickListener {
                navigateToModifyFavourites()
            }

            settingsFragment_btn_save.setOnClickListener {
                confirmSettings()
            }

        }
    }

    fun confirmSettings() {
        try {
            val locationId = settingsFragment_spn_location.selectedItemPosition
            val location = try {
                locations[locationId].id
            } catch (e: ArrayIndexOutOfBoundsException) {
                showSelectLocationMessage()
                return
            }
            val refreshRateId = settingsFragment_spn_refreshRate.selectedItemPosition
            val refreshRate = refreshRateSpinnerValues[refreshRateId].toDouble()
            val units = settingsFragment_spn_units.selectedItemPosition
            val settings = Settings(
                location,
                refreshRate,
                Settings.Units.values()[units]
            )
            viewModel.setSettings(settings)
            presenter.onSaveSettings()
            dismiss()
        } catch (e: NumberFormatException) {
            Toast.makeText(this@SettingsActivity, "Error while saving settings", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun showSelectLocationMessage() {
        Toast.makeText(applicationContext, "Please select location", Toast.LENGTH_SHORT).show()
    }

    override fun navigateToModifyFavourites() {
        val fm = supportFragmentManager
        val settingsDialogFragment = ModifyFavouritesDialogFragment()
        settingsDialogFragment.show(fm, "Fragment_tag")
    }

    override fun subscribeFavourites() {
        viewModel.favourites().observe(this, locationsObserver)
    }

    override fun unsubscribeFavourites() {
        viewModel.favourites().removeObserver { locationsObserver }
    }

    override fun subscribeSettings() {
        viewModel.settings().observe(this, settingsObserver)
    }

    override fun unsubscribeSettings() {
        viewModel.settings().removeObserver { settingsObserver }
    }

    override fun dismiss() {
        finish()
    }

    private fun navigateToAstroWeather() {
        val intent = Intent(applicationContext, AstroWeatherActivity::class.java)
        startActivity(intent)
    }

    private fun updateLocations() = launch {
        locationAdapter = ArrayAdapter(
            this@SettingsActivity,
            R.layout.support_simple_spinner_dropdown_item,
            locations.map {
                "${it.id}: ${it.name} " +
                        "${if(it.state.isNotBlank()) it.state + "/" else ""}${it.country}"
            }
        )
        settingsFragment_spn_location.adapter = locationAdapter
    }

    private fun updateSettings() = launch {
        settings?.let { settings ->
            val locationId = locations.indexOfFirst { it.id == settings.location_id }
            if(locationId != Spinner.NO_ID) {
                settingsFragment_spn_location.setSelection(locationId)
            }

            val refreshRateId = refreshRateSpinnerValues.indexOf(settings.dataRefreshFrequency.toInt().toString())
            if(refreshRateId != Spinner.NO_ID) {
                settingsFragment_spn_refreshRate.setSelection(refreshRateId)
            }

            val unitsId = settings.units.ordinal
            settingsFragment_spn_units.setSelection(unitsId)

        }
    }

}