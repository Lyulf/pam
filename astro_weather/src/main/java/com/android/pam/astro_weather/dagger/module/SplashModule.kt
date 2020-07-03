package com.android.pam.astro_weather.dagger.module

import android.content.Context
import com.android.pam.astro_weather.dagger.scope.ActivityScope
import com.android.pam.astro_weather.data.database.AstroWeatherRoomDatabase
import com.android.pam.astro_weather.domain.usecase.InitDatabaseUseCase
import com.android.pam.astro_weather.presentation.contract.ISplashContract
import com.android.pam.astro_weather.presentation.controller.SplashController
import dagger.Module
import dagger.Provides

@Module
class SplashModule {
    @Provides
    @ActivityScope
    fun provideInitDatabaseUseCase(
        database: AstroWeatherRoomDatabase,
        context: Context
    ): InitDatabaseUseCase = InitDatabaseUseCase(database, context)

    @Provides
    @ActivityScope
    fun provideISpashController(
        initDatabaseUseCase: InitDatabaseUseCase
    ): ISplashContract.IController = SplashController(initDatabaseUseCase)
}