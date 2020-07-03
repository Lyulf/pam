package com.android.pam.astro_weather.data.wrapper

import com.android.pam.astro_weather.data.converter.IAstroDateTimeConverter
import com.android.pam.astro_weather.domain.model.location.Coordinates
import com.android.pam.astro_weather.domain.model.moon.*
import com.android.pam.astro_weather.domain.model.sun.*
import com.android.pam.astro_weather.domain.repository.IAstroWeatherSettingsRepository
import com.android.pam.astro_weather.domain.wrapper.IAstrology
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class AstroCalculatorWrapper @Inject constructor(
    val repository: IAstroWeatherSettingsRepository,
    val converter: IAstroDateTimeConverter
) : IAstrology {
    init {
        refresh()
    }

    private lateinit var astroDateTime: AstroDateTime
    private var location: AstroCalculator.Location? = null
    private val astroCalculator: AstroCalculator
        get() {
            return AstroCalculator(astroDateTime, location)
        }

    override fun refresh() {
        astroDateTime = ZonedDateTime.now().toAstro()
    }

    override suspend fun updateSettings() {
        location = repository.getLocation()?.toAstroLocation()
    }

    override fun moon(): Moon {
        val moonInfo = try {
            astroCalculator.moonInfo
        } catch(e: NullPointerException) {
            null
        }
        val moonrise = Moonrise(
            moonInfo?.moonrise?.toOffsetTime()
        )
        val moonset = Moonset(
            moonInfo?.moonset?.toOffsetTime()
        )
        val nextNewMoon = NewMoon(
            moonInfo?.nextNewMoon?.toZonedDateTime()
        )
        val nextFullMoon = FullMoon(
            moonInfo?.nextFullMoon?.toZonedDateTime()
        )
        val moonPhase = MoonPhase(
            if(moonInfo?.illumination != null) moonInfo.illumination * 100 else null
        )
        val dayOfSynodicMonth = moonInfo?.age?.toInt()

        return Moon(
            moonrise, moonset, nextNewMoon, nextFullMoon, moonPhase, dayOfSynodicMonth
        )
    }

    override fun sun(): Sun {
        val sunInfo = try {
            astroCalculator.sunInfo
        } catch (e: NullPointerException) {
            null
        }
        val sunrise = Sunrise(
            sunInfo?.sunrise?.toOffsetTime(),
            sunInfo?.azimuthRise
        )
        val sunset = Sunset(
            sunInfo?.sunset?.toOffsetTime(),
            sunInfo?.azimuthSet
        )

        val civilDusk = Dusk(
            sunInfo?.twilightEvening?.toOffsetTime()
        )

        val civilDawn = Dawn(
            sunInfo?.twilightMorning?.toOffsetTime()
        )

        return Sun(sunrise, sunset, civilDusk, civilDawn)
    }

    private fun Coordinates.toAstroLocation(): AstroCalculator.Location? {
        return if(latitude != null && longitude != null) {
            AstroCalculator.Location(latitude, longitude)
        } else {
            null
        }
    }

    private fun AstroDateTime.toZonedDateTime(): ZonedDateTime {
        return converter.astroDateTimeToZonedDateTime(this)
    }

    private fun AstroDateTime.toOffsetTime(): OffsetTime {
        return toZonedDateTime().toOffsetDateTime().toOffsetTime()
    }

    private fun ZonedDateTime.toAstro(): AstroDateTime {
        return converter.zonedDateTimeToAstroDateTime(this)
    }
}