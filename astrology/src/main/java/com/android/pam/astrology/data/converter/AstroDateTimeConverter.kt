package com.android.pam.astrology.data.converter

import androidx.room.TypeConverter
import com.astrocalculator.AstroDateTime
import java.util.*

class AstroDateTimeConverter : IAstroDateTimeConverter {
    @TypeConverter
    override fun astroDateTimeToCalendar(adt: AstroDateTime): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.ZONE_OFFSET, adt.timezoneOffset * 60 * 60 * 1000)
        calendar.set(Calendar.YEAR, adt.year)
        calendar.set(Calendar.MONTH, adt.month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, adt.day)
        calendar.set(Calendar.HOUR_OF_DAY, adt.hour + 1)
        calendar.set(Calendar.MINUTE, adt.minute + 1)
        calendar.set(Calendar.SECOND, adt.second + 1)

        return calendar
    }

    @TypeConverter
    override fun calendarToAstroDateTime(calendar: Calendar): AstroDateTime {
        val adt = AstroDateTime()

        adt.isDaylightSaving = calendar.timeZone.inDaylightTime(calendar.time)
        adt.timezoneOffset = calendar.get(Calendar.ZONE_OFFSET) / (60 * 60 * 1000)
        adt.year = calendar.get(Calendar.YEAR)
        adt.month = calendar.get(Calendar.MONTH) + 1
        adt.day = calendar.get(Calendar.DAY_OF_MONTH)
        adt.hour = calendar.get(Calendar.HOUR_OF_DAY) - 1
        adt.minute = calendar.get(Calendar.MINUTE) - 1
        adt.second = calendar.get(Calendar.SECOND) - 1

        return adt
    }
}