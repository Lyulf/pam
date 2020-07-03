package com.android.pam.astro_weather.presentation.view.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.pam.astro_weather.AstroWeatherApp
import com.android.pam.astro_weather.R
import com.android.pam.astro_weather.dagger.component.AstroWeatherComponent
import com.android.pam.astro_weather.dagger.component.DaggerAstroWeatherComponent
import com.android.pam.astro_weather.dagger.module.ActivitiesModule
import com.android.pam.astro_weather.presentation.contract.IAstroWeatherContract
import com.android.pam.astro_weather.presentation.view.fragment.*
import kotlinx.android.synthetic.main.activity_astroweather.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AstroWeatherActivity : AppCompatActivity(), CoroutineScope, IAstroWeatherContract.IView {
    @Inject lateinit var viewModel: IAstroWeatherContract.IViewModel
    @Inject lateinit var presenter: IAstroWeatherContract.IPresenter

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private lateinit var viewPagerAdapter: AstroPagerAdapter
    private val timeObserver = Observer<LocalTime> {time ->
        launch {
            supportActionBar?.title = getString(R.string.action_time).format(
                time.withNano(0).format(DateTimeFormatter.ISO_LOCAL_TIME)
            )
        }
    }

    private lateinit var component: AstroWeatherComponent

    override fun astroWeatherComponent(): AstroWeatherComponent {
        return component
    }

    override fun navigateToSettings() {
        val intent = Intent(applicationContext, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun showDataRefreshedMessage() {
        Toast.makeText(applicationContext, "Data updated", Toast.LENGTH_SHORT).show()
    }

    override fun showDownloadError(lastUpdate: LocalDateTime?) {
        astroWeatherFragment_txv_noConnection.text = getString(R.string.aw_no_connection).format(
            lastUpdate?.let {
                DateTimeFormatter.ofPattern("HH:mm dd MMMM yyyy").format(it)
            } ?: "never"
        )
        astroWeatherFragment_txv_noConnection.visibility = View.VISIBLE
    }

    override fun hideDownloadError() {
        astroWeatherFragment_txv_noConnection.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        component = DaggerAstroWeatherComponent.builder()
            .applicationComponent((application as AstroWeatherApp).appComponent())
            .activitiesModule(ActivitiesModule(this)).build()
        component.inject(this)
        super.onCreate(savedInstanceState)

        launch {
            setContentView(R.layout.activity_astroweather)
            setupViewPager()
            setupSupportActionBar()

            presenter.onAttach(this@AstroWeatherActivity)

        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    private fun setupViewPager() {
        viewPagerAdapter = AstroPagerAdapter(this)
        astroWeatherFragment_viewPager!!.apply {
            adapter = viewPagerAdapter
            offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    selectedTabPosition = position
//                    populateUI(transaction)
                }
            })
            currentItem = selectedTabPosition
        }
    }

    private fun setupSupportActionBar() {
        subscribeTime()
    }

    override fun subscribeTime() {
        viewModel.time().observe(this, timeObserver)
    }

    override fun unsubscribeTime() {
        viewModel.time().removeObserver(timeObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.toolbar_act_refresh -> launch { presenter.onUpdate() }
            R.id.toolbar_act_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
//                val fm = supportFragmentManager
//                val settingsDialogFragment = SettingsDialogFragment()
//                settingsDialogFragment.show(fm, "Fragment_tag")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private var selectedTabPosition = 0
    }

    private class AstroPagerAdapter(
        private val fragmentActivity: FragmentActivity
    ) : FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {
            if(layoutIsLarge) {
                return when(position) {
                    ASTRO_FRAGMENT_POSITION -> AstroFragment()
                    WEATHER_FRAGMENT_POSITION -> WeatherFragment()
                    else -> throw IllegalStateException("Invalid adapter position")
                }
            }
            else {
                return when (position) {
                    SUN_FRAGMENT_POSITION -> SunFragment()
                    MOON_FRAGMENT_POSITION -> MoonFragment()
                    BASIC_WEATHER_FRAGMENT_POSITION -> BasicWeatherFragment()
                    ADDITIONAL_WEATHER_FRAGMENT_POSITION -> AdditionalWeatherFragment()
                    FORECAST_FRAGMENT_POSITION -> ForecastFragment()
                    else -> throw IllegalStateException("Invalid adapter position")
                }
            }
        }

        override fun getItemCount(): Int {
            return if(layoutIsLarge) LARGE_PAGER_SCREENS_NUMBER else PAGER_SCREENS_NUMBER
        }

        private val layoutIsLarge: Boolean
            get() {
                return fragmentActivity.resources.configuration.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)
            }

        companion object {
            internal const val SUN_FRAGMENT_POSITION = 0
            internal const val MOON_FRAGMENT_POSITION = 1
            internal const val BASIC_WEATHER_FRAGMENT_POSITION = 2
            internal const val ADDITIONAL_WEATHER_FRAGMENT_POSITION = 3
            internal const val FORECAST_FRAGMENT_POSITION = 4
            internal const val PAGER_SCREENS_NUMBER = 5

            internal const val ASTRO_FRAGMENT_POSITION = 0
            internal const val WEATHER_FRAGMENT_POSITION = 1
            internal const val LARGE_PAGER_SCREENS_NUMBER = 2
        }
    }
}