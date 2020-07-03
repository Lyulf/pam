package com.android.pam.astro_weather.dagger.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.android.pam.astro_weather.dagger.scope.ActivityScope
import com.android.pam.astro_weather.data.dao.CityDao
import com.android.pam.astro_weather.data.dao.WeatherDao
import com.android.pam.astro_weather.data.repository.IWeatherApi
import com.android.pam.astro_weather.data.repository.WeatherRepository
import com.android.pam.astro_weather.data.wrapper.OpenWeatherMapWrapper
import com.android.pam.astro_weather.domain.repository.IWeatherRepository
import com.android.pam.astro_weather.domain.usecase.GetSettingsUseCase
import com.android.pam.astro_weather.domain.usecase.GetWeatherDataUseCase
import com.android.pam.astro_weather.presentation.contract.IAdditionalWeatherContract
import com.android.pam.astro_weather.presentation.contract.IBasicWeatherContract
import com.android.pam.astro_weather.presentation.contract.IForecastContract
import com.android.pam.astro_weather.presentation.presenter.AdditionalWeatherPresenter
import com.android.pam.astro_weather.presentation.presenter.BasicWeatherPresenter
import com.android.pam.astro_weather.presentation.presenter.ForecastPresenter
import com.android.pam.astro_weather.presentation.viewmodel.WeatherViewModel
import dagger.Module
import dagger.Provides

@Module
class WeatherModule {
    @Provides
    @ActivityScope
    fun provideIBasicWeatherViewModel(
        activity: FragmentActivity
    ) : IBasicWeatherContract.IViewModel = ViewModelProvider(activity).get(WeatherViewModel::class.java)

    @Provides
    @ActivityScope
    fun provideIAdditionalWeatherViewModel(
    activity: FragmentActivity
    ) : IAdditionalWeatherContract.IViewModel = ViewModelProvider(activity).get(WeatherViewModel::class.java)

    @Provides
    @ActivityScope
    fun provideIForecastViewModel(
    activity: FragmentActivity
    ) : IForecastContract.IViewModel = ViewModelProvider(activity).get(WeatherViewModel::class.java)

    @Provides
    @ActivityScope
    fun provideIBasicWeatherPresenter(
        getSettingsUseCase: GetSettingsUseCase,
        getWeatherDataUseCase: GetWeatherDataUseCase,
        viewModel: IBasicWeatherContract.IViewModel
    ) : IBasicWeatherContract.IPresenter = BasicWeatherPresenter(getSettingsUseCase, getWeatherDataUseCase, viewModel)

    @Provides
    @ActivityScope
    fun provideIAdditionalWeatherPresenter(
        getSettingsUseCase: GetSettingsUseCase,
        getWeatherDataUseCase: GetWeatherDataUseCase,
        viewModel: IAdditionalWeatherContract.IViewModel
    ): IAdditionalWeatherContract.IPresenter = AdditionalWeatherPresenter(getSettingsUseCase, getWeatherDataUseCase, viewModel)

    @Provides
    @ActivityScope
    fun provideIForecastPresenter(
        getSettingsUseCase: GetSettingsUseCase,
        getWeatherDataUseCase: GetWeatherDataUseCase,
        viewModel: IForecastContract.IViewModel
    ): IForecastContract.IPresenter = ForecastPresenter(getSettingsUseCase, getWeatherDataUseCase, viewModel)

    @Provides
    @ActivityScope
    fun provideIWeatherRepository(dao: WeatherDao, api: IWeatherApi): IWeatherRepository = WeatherRepository(dao, api)

    @Provides
    @ActivityScope
    fun provideIWeatherApi(dao: CityDao): IWeatherApi = OpenWeatherMapWrapper(dao)


}