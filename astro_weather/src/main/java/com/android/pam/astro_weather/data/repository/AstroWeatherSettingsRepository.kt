package com.android.pam.astro_weather.data.repository

import com.android.pam.astro_weather.data.converter.CoordinatesConverter.toCoordinates
import com.android.pam.astro_weather.data.converter.SettingsConverter.toSettings
import com.android.pam.astro_weather.data.converter.SettingsConverter.toSettingsEntity
import com.android.pam.astro_weather.data.dao.CityDao
import com.android.pam.astro_weather.data.dao.SettingsDao
import com.android.pam.astro_weather.domain.model.location.Coordinates
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.repository.IAstroWeatherSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AstroWeatherSettingsRepository @Inject constructor(
    private val settingsDao: SettingsDao,
    private val cityDao: CityDao
) : IAstroWeatherSettingsRepository, CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun setSettings(settings: Settings) {
        val current = settings.toSettingsEntity()
        try {
            val previous = settingsDao.getSettings()
            current.refreshDatabase = previous.refreshDatabase
        } catch (e: NullPointerException) {
            current.refreshDatabase = true
        }
        finally {
            settingsDao.setSettings(current)
        }
    }

    override fun getSettings(): Settings {
        return settingsDao.getSettings().toSettings()
    }

    override suspend fun getLocation(): Coordinates? {
        val id = settingsDao.getLocation() ?: return null
        val city = cityDao.getCity(id)
        return city!!.coord.toCoordinates()
    }

    override fun getRefreshRate(): Double {
        return settingsDao.getRefreshFrequency()
    }

}