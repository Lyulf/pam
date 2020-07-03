package com.android.pam.astro_weather.dagger.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.android.pam.astro_weather.dagger.scope.ActivityScope
import com.android.pam.astro_weather.domain.repository.IAstroWeatherSettingsRepository
import com.android.pam.astro_weather.domain.usecase.*
import com.android.pam.astro_weather.presentation.contract.IFavouritesContract
import com.android.pam.astro_weather.presentation.contract.ISettingsContract
import com.android.pam.astro_weather.presentation.presenter.FavouritesPresenter
import com.android.pam.astro_weather.presentation.presenter.SettingsPresenter
import com.android.pam.astro_weather.presentation.viewmodel.FavouritesViewModel
import com.android.pam.astro_weather.presentation.viewmodel.SettingsViewModel
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {
    @Provides
    @ActivityScope
    fun provideISettingsViewModel(activity: FragmentActivity)
            : ISettingsContract.IViewModel = ViewModelProvider(activity).get(SettingsViewModel::class.java)

    @Provides
    @ActivityScope
    fun provideISettingsPresenter(
        getSettingsUseCase: GetSettingsUseCase,
        saveSettingsUseCase: SaveSettingsUseCase,
        getFavouriteCitiesUseCase: GetFavouriteCitiesUseCase,
        viewModel: ISettingsContract.IViewModel
    ) : ISettingsContract.IPresenter = SettingsPresenter(
        getSettingsUseCase, saveSettingsUseCase, getFavouriteCitiesUseCase, viewModel)

    @Provides
    @ActivityScope
    fun provideIFavouritesViewModel(activity: FragmentActivity)
            : IFavouritesContract.IViewModel = ViewModelProvider(activity).get(FavouritesViewModel::class.java)

    @Provides
    @ActivityScope
    fun provideIFavouritesPresenter(
        getCitiesUseCase: GetCitiesUseCase,
        setCityUseCase: SetCityUseCase,
        settingsPresenter: ISettingsContract.IPresenter,
        viewModel: IFavouritesContract.IViewModel
    ) : IFavouritesContract.IPresenter = FavouritesPresenter(getCitiesUseCase, setCityUseCase, settingsPresenter, viewModel)

    @Provides
    @ActivityScope
    fun provideSaveSettingsUseCase(
        repository: IAstroWeatherSettingsRepository
    ): SaveSettingsUseCase = SaveSettingsUseCase(repository)
}