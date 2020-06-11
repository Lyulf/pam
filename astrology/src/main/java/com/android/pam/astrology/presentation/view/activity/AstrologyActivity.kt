package com.android.pam.astrology.presentation.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.pam.astrology.R
import com.android.pam.astrology.di.component.AstrologyComponent
import com.android.pam.astrology.di.component.DaggerAstrologyComponent
import com.android.pam.astrology.di.module.ActivitiesModule
import com.android.pam.astrology.presentation.contract.IAstrologyContract
import com.android.pam.astrology.presentation.contract.IMoonContract
import com.android.pam.astrology.presentation.contract.ISunContract
import com.android.pam.astrology.presentation.view.fragment.MoonFragment
import com.android.pam.astrology.presentation.view.fragment.SettingsDialogFragment
import com.android.pam.astrology.presentation.view.fragment.SunFragment
import kotlinx.android.synthetic.main.activity_astro.*
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class AstrologyActivity : AppCompatActivity(), IAstrologyContract.IView {
    @Inject lateinit var viewModel: IAstrologyContract.IViewModel
    @Inject lateinit var sunViewModel: ISunContract.IViewModel
    @Inject lateinit var moonViewModel: IMoonContract.IViewModel
    @Inject lateinit var presenter: IAstrologyContract.IPresenter
    private lateinit var viewPagerAdapter: AstroPagerAdapter
    private val timeObserver = Observer<LocalTime> {
        time -> supportActionBar?.title = getString(R.string.action_time).format(
            time.withNano(0).format(DateTimeFormatter.ISO_LOCAL_TIME)
        )
    }
    private lateinit var component: AstrologyComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        component = DaggerAstrologyComponent.builder().activitiesModule(ActivitiesModule(this)).build()
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_astro)


        if(viewPager != null) {
            setupViewPager()
        }
        setupSuportActionBar()
    }

    override fun astrologyComponent(): AstrologyComponent {
        return component
    }

    override fun onResume() {
        super.onResume()
        presenter.startRefreshingTime(1000)
        presenter.startUpdatingData()
    }

    override fun onPause() {
        presenter.stopRefreshingTime()
        presenter.stopUpdatingData()
        super.onPause()
    }

    private fun setupViewPager() {
        viewPagerAdapter = AstroPagerAdapter(this)
        viewPager!!.apply {
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

    private fun setupSuportActionBar() {
        subscribeTime()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.toolbar_act_refresh -> presenter.onUpdateData()
            R.id.toolbar_act_settings -> {
                val fm = supportFragmentManager
                val settingsDialogFragment = SettingsDialogFragment()
                settingsDialogFragment.show(fm, "Fragment_tag")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun subscribeTime() {
        viewModel.time().observe(this, timeObserver)
    }

    override fun unsubscribeTime() {
        viewModel.time().removeObserver(timeObserver)
    }

    companion object {
        private var selectedTabPosition = 0
    }

    private class AstroPagerAdapter(
        fragmentActivity: FragmentActivity
    ) : FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment = when(position) {
            SUN_FRAGMENT_POSITION -> SunFragment()
            MOON_FRAGMENT_POSITION -> MoonFragment()
            else -> throw IllegalStateException("Invalid adapter position")
        }

        override fun getItemCount(): Int = PAGER_SCREENS_NUMBER

        companion object {
            internal const val SUN_FRAGMENT_POSITION = 0
            internal const val MOON_FRAGMENT_POSITION = 1
            internal const val PAGER_SCREENS_NUMBER = 2
        }
    }
}