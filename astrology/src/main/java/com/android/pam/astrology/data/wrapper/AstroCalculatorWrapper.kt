package com.android.pam.astrology.data.wrapper

import com.android.pam.astrology.data.converter.IAstroDateTimeConverter
import com.android.pam.astrology.domain.model.moon.*
import com.android.pam.astrology.domain.model.settings.Location
import com.android.pam.astrology.domain.model.sun.*
import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository
import com.android.pam.astrology.domain.wrapper.IAstrology
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class AstroCalculatorWrapper @Inject constructor(
    val repository: IAstrologySettingsRepository,
    val converter: IAstroDateTimeConverter
) : IAstrology {
    init {
        updateSettings()
        refresh()
    }

    private lateinit var astroDateTime: AstroDateTime
    private lateinit var location: AstroCalculator.Location
    private val astroCalculator: AstroCalculator
        get() {
            return AstroCalculator(astroDateTime, location)
        }

    override fun refresh() {
        astroDateTime = ZonedDateTime.now().toAstro()
    }

    override fun updateSettings() {
        location = repository.getLocation().toAstroLocation()
    }

    override fun moon(): Moon {
        val moonInfo = astroCalculator.moonInfo
        val moonrise = Moonrise(
            moonInfo.moonrise?.toOffsetTime()
        )
        val moonset = Moonset(
            moonInfo.moonset?.toOffsetTime()
        )
        val nextNewMoon = NewMoon(
            moonInfo.nextNewMoon?.toZonedDateTime()
        )
        val nextFullMoon = FullMoon(
            moonInfo.nextFullMoon?.toZonedDateTime()
        )
        val moonPhase = MoonPhase(
            moonInfo.illumination * 100
        )
        val dayOfSynodicMonth = moonInfo.age.toInt()

        return Moon(
            moonrise, moonset, nextNewMoon, nextFullMoon, moonPhase, dayOfSynodicMonth
        )
    }

    override fun sun(): Sun {
        val sunInfo = astroCalculator.sunInfo
        val sunrise = Sunrise(
            sunInfo.sunrise?.toOffsetTime(),
            sunInfo.azimuthRise
        )
        val sunset = Sunset(
            sunInfo.sunset?.toOffsetTime(),
            sunInfo.azimuthSet
        )

        val civilDusk = Dusk(
            sunInfo.twilightEvening?.toOffsetTime()
        )

        val civilDawn = Dawn(
            sunInfo.twilightMorning?.toOffsetTime()
        )

        return Sun(sunrise, sunset, civilDusk, civilDawn)
    }

    private fun Location.toAstroLocation(): AstroCalculator.Location {
        return AstroCalculator.Location(latitude, longitude)
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