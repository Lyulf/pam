package com.android.pam.astrology.data.converter

import com.astrocalculator.AstroDateTime
import java.util.*

interface IAstroDateTimeConverter {
    fun astroDateTimeToCalendar(adt: AstroDateTime): Calendar
    fun calendarToAstroDateTime(calendar: Calendar): AstroDateTime
}