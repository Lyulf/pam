package com.android.pam.astro_weather.domain.usecase

import android.content.Context
import com.android.pam.astro_weather.data.database.AstroWeatherRoomDatabase
import javax.inject.Inject

class InitDatabaseUseCase @Inject constructor(
    private val db: AstroWeatherRoomDatabase,
    private val context: Context
){
    suspend fun invoke() {
        db.initialize(context)
    }
}