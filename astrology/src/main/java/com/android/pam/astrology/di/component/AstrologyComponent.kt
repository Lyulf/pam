package com.android.pam.astrology.di.component

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.android.pam.astrology.di.module.ActivitiesModule
import com.android.pam.astrology.di.module.AstrologyModule
import com.android.pam.astrology.di.module.RoomModule
import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository
import com.android.pam.astrology.presentation.view.activity.AstrologyActivity
import com.android.pam.astrology.presentation.view.fragment.MoonFragment
import com.android.pam.astrology.presentation.view.fragment.SettingsFragment
import com.android.pam.astrology.presentation.view.fragment.SunFragment
import dagger.Component

@Component(
    modules = [
        ActivitiesModule::class,
        AstrologyModule::class,
        RoomModule::class
    ]
)
interface AstrologyComponent {
    fun fragmentActivity(): FragmentActivity
    fun context(): Context
    fun astrologySettingsRepository(): IAstrologySettingsRepository

    fun inject(astrologyActivity: AstrologyActivity)
    fun inject(sunFragment: SunFragment)
    fun inject(moonFragment: MoonFragment)
    fun inject(settingsFragment: SettingsFragment)
}