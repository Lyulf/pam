package com.android.pam.astro_weather.data.converter

import com.astrocalculator.AstroDateTime
import org.threeten.bp.ZonedDateTime

interface IAstroDateTimeConverter {
    fun astroDateTimeToZonedDateTime(adt: AstroDateTime): ZonedDateTime
    fun zonedDateTimeToAstroDateTime(zdt: ZonedDateTime): AstroDateTime
}