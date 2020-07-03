package com.android.pam.astro_weather.dagger.module

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.android.pam.astro_weather.dagger.scope.ActivityScope
import com.android.pam.astro_weather.data.converter.AstroDateTimeConverter
import com.android.pam.astro_weather.data.converter.IAstroDateTimeConverter
import com.android.pam.astro_weather.data.wrapper.AstroCalculatorWrapper
import com.android.pam.astro_weather.data.wrapper.DeviceTimeImpl
import com.android.pam.astro_weather.domain.repository.IAstroWeatherSettingsRepository
import com.android.pam.astro_weather.domain.usecase.*
import com.android.pam.astro_weather.domain.wrapper.IAstrology
import com.android.pam.astro_weather.domain.wrapper.IDeviceTime
import com.android.pam.astro_weather.presentation.contract.*
import com.android.pam.astro_weather.presentation.presenter.AstroWeatherPresenter
import com.android.pam.astro_weather.presentation.presenter.MoonPresenter
import com.android.pam.astro_weather.presentation.presenter.SunPresenter
import com.android.pam.astro_weather.presentation.viewmodel.AstrologyViewModel
import com.android.pam.astro_weather.presentation.viewmodel.MoonViewModel
import com.android.pam.astro_weather.presentation.viewmodel.SunViewModel
import dagger.Module
import dagger.Provides

@Module
class AstrologyModule {
    @Provides
    fun provideContext(activity: FragmentActivity): Context = activity.applicationContext

    @Provides
    @ActivityScope
    fun provideIAstrologyViewModel(activity: FragmentActivity)
            : IAstroWeatherContract.IViewModel = ViewModelProvider(activity).get(AstrologyViewModel::class.java)

    @Provides
    @ActivityScope
    fun provideISunViewModel(activity: FragmentActivity)
        : ISunContract.IViewModel = ViewModelProvider(activity).get(SunViewModel::class.java)

    @Provides
    @ActivityScope
    fun provideIMoonViewModel(activity: FragmentActivity)
            : IMoonContract.IViewModel = ViewModelProvider(activity).get(MoonViewModel::class.java)

    @Provides
    @ActivityScope
    fun provideISunPresenter(
        refreshAstrologicalDataUseCase: RefreshAstrologicalDataUseCase,
        getSunDataUseCase: GetSunDataUseCase,
        sunViewModel: ISunContract.IViewModel
    ) : ISunContract.IPresenter = SunPresenter(
        refreshAstrologicalDataUseCase,
        getSunDataUseCase,
        sunViewModel
    )
    @Provides
    @ActivityScope
    fun provideIMoonPresenter(
        refreshAstrologicalDataUseCase: RefreshAstrologicalDataUseCase,
        getMoonDataUseCase: GetMoonDataUseCase,
        moonViewModel: IMoonContract.IViewModel
    ) : IMoonContract.IPresenter = MoonPresenter(
        refreshAstrologicalDataUseCase,
        getMoonDataUseCase,
        moonViewModel
    )

    @Provides
    @ActivityScope
    fun provideIAstrologyPresenter(
        getTimeUseCase: GetTimeUseCase,
        getDateTimeUseCase: GetDateTimeUseCase,
        getDataRefreshRateUseCase: GetDataRefreshRateUseCase,
        getSettingsUseCase: GetSettingsUseCase,
        downloadWeatherDataUseCase: DownloadWeatherDataUseCase,
        getWeatherDataUseCase: GetWeatherDataUseCase,
        viewModel: IAstroWeatherContract.IViewModel,
        sunPresenter: ISunContract.IPresenter,
        moonPresenter: IMoonContract.IPresenter,
        basicWeatherPresenter: IBasicWeatherContract.IPresenter,
        additionalWeatherPresenter: IAdditionalWeatherContract.IPresenter,
        forecastPresenter: IForecastContract.IPresenter
    ) : IAstroWeatherContract.IPresenter = AstroWeatherPresenter(
        getTimeUseCase,
        getDateTimeUseCase,
        getDataRefreshRateUseCase,
        getSettingsUseCase,
        downloadWeatherDataUseCase,
        getWeatherDataUseCase,
        viewModel,
        sunPresenter,
        moonPresenter,
        basicWeatherPresenter,
        additionalWeatherPresenter,
        forecastPresenter
    )

    @Provides
    @ActivityScope
    fun provideAstroDateTimeConverter(): IAstroDateTimeConverter = AstroDateTimeConverter()

    @Provides
    @ActivityScope
    fun provideIAstrology(
        repository: IAstroWeatherSettingsRepository,
        converter: IAstroDateTimeConverter
    ): IAstrology = AstroCalculatorWrapper(repository, converter)

    @Provides
    @ActivityScope
    fun provideIDeviceTime(): IDeviceTime = DeviceTimeImpl()

    @Provides
    @ActivityScope
    fun provideGetMoonDataUseCase(wrapper: IAstrology): GetMoonDataUseCase
            = GetMoonDataUseCase(wrapper)

    @Provides
    @ActivityScope
    fun provideGetSunDataUseCase(wrapper: IAstrology): GetSunDataUseCase
            = GetSunDataUseCase(wrapper)

    @Provides
    @ActivityScope
    fun provideGetTimeUseCase(wrapper: IDeviceTime): GetTimeUseCase
            = GetTimeUseCase(wrapper)

    @Provides
    @ActivityScope
    fun provideGetDateTimeUseCase(wrapper: IDeviceTime): GetDateTimeUseCase
            = GetDateTimeUseCase(wrapper)

    @Provides
    @ActivityScope
    fun provideRefreshAstrologicalDataUseCase(data: IAstrology): RefreshAstrologicalDataUseCase
            = RefreshAstrologicalDataUseCase(data)
}