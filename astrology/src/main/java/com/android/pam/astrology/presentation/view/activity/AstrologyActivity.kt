package com.android.pam.astrology.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.pam.astrology.R
import com.android.pam.astrology.di.component.DaggerAstrologyComponent
import com.android.pam.astrology.di.module.ActivitiesModule
import com.android.pam.astrology.presentation.contract.IAstrologyContract
import com.android.pam.astrology.presentation.contract.IMoonContract
import com.android.pam.astrology.presentation.contract.ISunContract
import com.android.pam.astrology.presentation.view.fragment.MoonFragment
import com.android.pam.astrology.presentation.view.fragment.SunFragment
import kotlinx.android.synthetic.main.activity_astro.*
import org.threeten.bp.LocalTime
import javax.inject.Inject

class AstrologyActivity : AppCompatActivity(), IAstrologyContract.IView {
    @Inject lateinit var viewModel: IAstrologyContract.IViewModel
    @Inject lateinit var sunViewModel: ISunContract.IViewModel
    @Inject lateinit var moonViewModel: IMoonContract.IViewModel
    private lateinit var viewPagerAdapter: AstroPagerAdapter
    private var actionBar: ActionBar? = null
    private val timeObserver = Observer<LocalTime> {
        time -> actionBar?.subtitle = time.toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAstrologyComponent.builder().activitiesModule(ActivitiesModule(this)).build().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_astro)

        setupViewPager()
//        setSupportActionBar(toolbar)
//        val actionBar = supportActionBar
//        actionBar!!.title = getString(R.string.app_name)
//        subscribeTime()
    }

    private fun setupViewPager() {
        viewPagerAdapter = AstroPagerAdapter(this)
        viewPager.apply {
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