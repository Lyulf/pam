package com.android.pam.astrology.data.converter

import com.astrocalculator.AstroDateTime
import org.threeten.bp.ZonedDateTime

interface IAstroDateTimeConverter {
    fun astroDateTimeToZonedDateTime(adt: AstroDateTime): ZonedDateTime
    fun zonedDateTimeToAstroDateTime(zdt: ZonedDateTime): AstroDateTime
}