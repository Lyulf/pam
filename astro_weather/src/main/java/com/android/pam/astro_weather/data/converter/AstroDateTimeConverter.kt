package com.android.pam.astro_weather.data.converter

import androidx.room.TypeConverter
import com.astrocalculator.AstroDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class AstroDateTimeConverter : IAstroDateTimeConverter {
    @TypeConverter
    override fun astroDateTimeToZonedDateTime(adt: AstroDateTime): ZonedDateTime {
        return ZonedDateTime.of(
            adt.year,
            adt.month,
            adt.day,
            adt.hour,
            adt.minute,
            adt.second,
            0,
            ZoneOffset.ofHours(adt.timezoneOffset)
        )
    }

    @TypeConverter
    override fun zonedDateTimeToAstroDateTime(zdt: ZonedDateTime): AstroDateTime {
        val adt = AstroDateTime()
        adt.isDaylightSaving = zdt.zone.rules.isDaylightSavings(zdt.toInstant())
        adt.timezoneOffset = zdt.offset.totalSeconds / (60 * 60)
        adt.year = zdt.year
        adt.month = zdt.monthValue
        adt.day = zdt.dayOfMonth
        adt.hour = zdt.hour
        adt.minute = zdt.minute
        adt.second = zdt.second

        return adt
    }
}