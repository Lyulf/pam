package com.android.pam.astro_weather.dagger.component

import com.android.pam.astro_weather.dagger.module.ActivitiesModule
import com.android.pam.astro_weather.dagger.module.SettingsModule
import com.android.pam.astro_weather.dagger.scope.ActivityScope
import com.android.pam.astro_weather.presentation.view.activity.SettingsActivity
import com.android.pam.astro_weather.presentation.view.fragment.ModifyFavouritesDialogFragment
import dagger.Component

@ActivityScope
@Component(
    modules = [
        ActivitiesModule::class,
        SettingsModule::class
    ],
    dependencies = [ApplicationComponent::class]
)
interface SettingsComponent {
    fun inject(activity: SettingsActivity)
    fun inject(fragment: ModifyFavouritesDialogFragment)
}