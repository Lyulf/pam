package com.android.pam.astrology.data.repository

import com.android.pam.astrology.data.converter.AstrologySettingsConverter
import com.android.pam.astrology.data.dao.AstrologySettingsDao
import com.android.pam.astrology.domain.model.settings.Location
import com.android.pam.astrology.domain.model.settings.Settings
import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AstrologySettingsRepository @Inject constructor(
    private val dao: AstrologySettingsDao
) : IAstrologySettingsRepository, CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val converter = AstrologySettingsConverter()

    override fun setSettings(settings: Settings) {
        launch {
            val astroSettings =
                converter.settingsToAstrologySettings(settings)
            dao.setSettings(astroSettings)

        }
    }

    override fun getSettings(): Settings {
        return runBlocking(coroutineContext) {
            val settings = dao.getSettings()
            if(settings.isEmpty.blockingGet()) {
                Settings()
            } else {
                converter.astrologySettingsToSettings(
                    settings.blockingGet()
                )
            }

        }
    }

    override fun getLocation(): Location {
        return runBlocking(coroutineContext) {
            dao.getLocation().blockingGet(
                Location()
            )
        }
    }

    override fun getRefreshRate(): Double {
        return runBlocking(coroutineContext) {
            dao.getRefreshFrequency().blockingGet(
                Settings().astroDataRefreshFrequency
            )
        }
    }

}