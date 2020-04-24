package com.android.pam.astrology.di.module

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.android.pam.astrology.data.converter.AstroDateTimeConverter
import com.android.pam.astrology.data.converter.IAstroDateTimeConverter
import com.android.pam.astrology.data.wrapper.AstroCalculatorWrapper
import com.android.pam.astrology.data.wrapper.DeviceTimeImpl
import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository
import com.android.pam.astrology.domain.usecase.*
import com.android.pam.astrology.domain.wrapper.IAstrology
import com.android.pam.astrology.domain.wrapper.IDeviceTime
import com.android.pam.astrology.presentation.contract.IAstrologyContract
import com.android.pam.astrology.presentation.contract.IMoonContract
import com.android.pam.astrology.presentation.contract.ISettingsContract
import com.android.pam.astrology.presentation.contract.ISunContract
import com.android.pam.astrology.presentation.presenter.AstrologyPresenter
import com.android.pam.astrology.presentation.presenter.MoonPresenter
import com.android.pam.astrology.presentation.presenter.SettingsPresenter
import com.android.pam.astrology.presentation.presenter.SunPresenter
import com.android.pam.astrology.presentation.viewmodel.AstrologyViewModel
import com.android.pam.astrology.presentation.viewmodel.MoonViewModel
import com.android.pam.astrology.presentation.viewmodel.SettingsViewModel
import com.android.pam.astrology.presentation.viewmodel.SunViewModel
import dagger.Module
import dagger.Provides

@Module
class AstrologyModule {
    @Provides
    fun provideContext(activity: FragmentActivity): Context = activity.applicationContext

    @Provides
    fun provideIAstrologyViewModel(activity: FragmentActivity)
            : IAstrologyContract.IViewModel = ViewModelProvider(activity).get(AstrologyViewModel::class.java)

    @Provides
    fun provideISunViewModel(activity: FragmentActivity)
        : ISunContract.IViewModel = ViewModelProvider(activity).get(SunViewModel::class.java)

    @Provides
    fun provideIMoonViewModel(activity: FragmentActivity)
            : IMoonContract.IViewModel = ViewModelProvider(activity).get(MoonViewModel::class.java)

    @Provides
    fun provideISettingsViewModel(activity: FragmentActivity)
            : ISettingsContract.IViewModel = ViewModelProvider(activity).get(SettingsViewModel::class.java)

    @Provides
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
    fun provideISettingsPresenter(
        getAstrologicalSettingsUseCase: GetAstrologicalSettingsUseCase,
        saveAstrologySettingsUseCase: SaveAstrologySettingsUseCase,
        viewModel: ISettingsContract.IViewModel,
        astrologyPresenter: IAstrologyContract.IPresenter
    ) : ISettingsContract.IPresenter = SettingsPresenter(
        getAstrologicalSettingsUseCase, saveAstrologySettingsUseCase, viewModel, astrologyPresenter)

    @Provides
    fun provideIAstrologyPresenter(
        getTimeUseCase: GetTimeUseCase,
        getDateTimeUseCase: GetDateTimeUseCase,
        getDataRefreshRateUseCase: GetDataRefreshRateUseCase,
        viewModel: IAstrologyContract.IViewModel,
        sunPresenter: ISunContract.IPresenter,
        moonPresenter: IMoonContract.IPresenter
    ) : IAstrologyContract.IPresenter = AstrologyPresenter(
        getTimeUseCase, getDateTimeUseCase, getDataRefreshRateUseCase, viewModel, sunPresenter, moonPresenter)

    @Provides
    fun provideAstroDateTimeConverter(): IAstroDateTimeConverter = AstroDateTimeConverter()

    @Provides
    fun provideIAstrology(
        repository: IAstrologySettingsRepository,
        converter: IAstroDateTimeConverter
    ): IAstrology = AstroCalculatorWrapper(repository, converter)

    @Provides
    fun provideIDeviceTime(): IDeviceTime = DeviceTimeImpl()

    @Provides
    fun provideGetAstrologicalSettingsUseCase(
        repository: IAstrologySettingsRepository
    ): GetAstrologicalSettingsUseCase = GetAstrologicalSettingsUseCase(repository)

    @Provides
    fun provideGetDataRefreshRateUseCase(
        repository: IAstrologySettingsRepository
    ): GetDataRefreshRateUseCase = GetDataRefreshRateUseCase(repository)

    @Provides
    fun provideGetMoonDataUseCase(wrapper: IAstrology): GetMoonDataUseCase
            = GetMoonDataUseCase(wrapper)

    @Provides
    fun provideGetSunDataUseCase(wrapper: IAstrology): GetSunDataUseCase
            = GetSunDataUseCase(wrapper)

    @Provides
    fun provideGetTimeUseCase(wrapper: IDeviceTime): GetTimeUseCase
            = GetTimeUseCase(wrapper)

    @Provides
    fun provideGetDateTimeUseCase(wrapper: IDeviceTime): GetDateTimeUseCase
            = GetDateTimeUseCase(wrapper)

    @Provides
    fun provideRefreshAstrologicalDataUseCase(data: IAstrology): RefreshAstrologicalDataUseCase
            = RefreshAstrologicalDataUseCase(data)

    @Provides
    fun provideSaveAstrologySettingsUseCase(
        repository: IAstrologySettingsRepository,
        wrapper: IAstrology
    ): SaveAstrologySettingsUseCase = SaveAstrologySettingsUseCase(repository, wrapper)
}