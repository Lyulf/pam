package com.android.pam.astrology.di.module

import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides

@Module
class ActivitiesModule(private val activity: FragmentActivity) {
//    @ContributesAndroidInjector(modules = [AstrologyModule::class, RoomModule::class])
//    abstract fun contributeActivityAndroidInjector(): AstrologyActivity
    @Provides
    fun provideFragmentActivity(): FragmentActivity = activity
}