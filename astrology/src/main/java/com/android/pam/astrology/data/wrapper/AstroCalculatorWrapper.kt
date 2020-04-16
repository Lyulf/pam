package com.android.pam.astrology.data.wrapper

import com.android.pam.astrology.data.converter.IAstroDateTimeConverter
import com.android.pam.astrology.domain.model.moon.*
import com.android.pam.astrology.domain.model.settings.Location
import com.android.pam.astrology.domain.model.sun.*
import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository
import com.android.pam.astrology.domain.wrapper.IAstrology
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import java.sql.Time
import java.util.*
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
        val cal = Calendar.getInstance()
        astroDateTime = cal.toAstro()
    }

    override fun updateSettings() {
        location = repository.getLocation().toAstroLocation()
    }

    override fun moon(): Moon {
        val moonInfo = astroCalculator.moonInfo
        val moonrise = Moonrise(
            moonInfo.moonrise.toTime()
        )
        val moonset = Moonset(
            moonInfo.moonset.toTime()
        )
        val nextNewMoon = NewMoon(
            moonInfo.nextNewMoon.toDate()
        )
        val nextFullMoon = FullMoon(
            moonInfo.nextFullMoon.toDate()
        )
        val moonPhase = MoonPhase(
            moonInfo.illumination
        )
        val dayOfSynodicMonth = moonInfo.age.toInt()

        return Moon(
            moonrise, moonset, nextNewMoon, nextFullMoon, moonPhase, dayOfSynodicMonth
        )
    }

    override fun sun(): Sun {
        val sunInfo = astroCalculator.sunInfo
        val sunrise = Sunrise(
            sunInfo.sunrise.toTime(),
            sunInfo.azimuthRise
        )
        val sunset = Sunset(
            sunInfo.sunset.toTime(),
            sunInfo.azimuthSet
        )

        val civilDusk = Dusk(
            sunInfo.twilightEvening.toTime()
        )

        val civilDawn = Dawn(
            sunInfo.twilightMorning.toTime()
        )

        return Sun(sunrise, sunset, civilDusk, civilDawn)
    }

    private fun Location.toAstroLocation(): AstroCalculator.Location {
        return AstroCalculator.Location(latitude, longitude)
    }

    private fun Calendar.toTime(): Time {
        return Time(timeInMillis)
    }

    private fun AstroDateTime.toCalendar(): Calendar {
        return converter.astroDateTimeToCalendar(this)
    }

    private fun AstroDateTime.toTime(): Time {
        return toCalendar().toTime()
    }

    private fun Calendar.toAstro(): AstroDateTime {
        return converter.calendarToAstroDateTime(this)
    }

    private fun AstroDateTime.toDate(): Date {
        return toCalendar().time
    }


}