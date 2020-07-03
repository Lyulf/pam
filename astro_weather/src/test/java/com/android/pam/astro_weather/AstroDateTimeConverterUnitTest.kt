package com.android.pam.astro_weather

import com.android.pam.astro_weather.data.converter.AstroDateTimeConverter
import com.astrocalculator.AstroDateTime
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class AstroDateTimeConverterUnitTest : StringSpec({
    "calendarToAstroDateTime is correct" {
        val date = ZonedDateTime.of(
            2020, 4, 16, 11, 4, 9, 0, ZoneOffset.ofHours(1)
        )
        val converter = AstroDateTimeConverter()
        val adt = converter.zonedDateTimeToAstroDateTime(date)
        assertSoftly {
            adt.day shouldBe 16
            adt.month shouldBe 4
            adt.year shouldBe 2020
            adt.hour shouldBe 11
            adt.minute shouldBe 4
            adt.second shouldBe 9
        }
    }
    "astroDateTimeToCalendar is correct" {
        val adt = AstroDateTime(2020, 4, 16, 11, 4, 9, 1, true)
        val converter = AstroDateTimeConverter()
        val date = converter.astroDateTimeToZonedDateTime(adt)

        assertSoftly {
            date.dayOfMonth shouldBe 16
            date.monthValue shouldBe 4
            date.year shouldBe 2020
            date.hour shouldBe 11
            date.minute shouldBe 4
            date.second shouldBe 9
        }
    }
    "converting Calendar to AstroDateTime and reverting it should give the Calendar" {
        val date = ZonedDateTime.now().withNano(0).withFixedOffsetZone()
        val converter = AstroDateTimeConverter()
        val adt = converter.zonedDateTimeToAstroDateTime(date)
        val convertedDate = converter.astroDateTimeToZonedDateTime(adt)

        assertSoftly {
            convertedDate shouldBe date
            convertedDate shouldBe date
        }
    }
})