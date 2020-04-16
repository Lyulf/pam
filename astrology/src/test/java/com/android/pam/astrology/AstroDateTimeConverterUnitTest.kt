package com.android.pam.astrology

import com.android.pam.astrology.data.converter.AstroDateTimeConverter
import com.astrocalculator.AstroDateTime
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.text.SimpleDateFormat
import java.util.*

class AstroDateTimeConverterUnitTest : StringSpec({
    "calendarToAstroDateTime is correct" {
        val format = SimpleDateFormat("dd/MM/yyyy/HH/mm/ss")
        val date = format.parse("16/04/2020/12/05/10")
        val cal = Calendar.getInstance()
        cal.time = date
        val converter = AstroDateTimeConverter()
        val adt = converter.calendarToAstroDateTime(cal)
        cal.time = date
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
        val cal = converter.astroDateTimeToCalendar(adt)

        assertSoftly {
            cal.get(Calendar.DAY_OF_MONTH) shouldBe 16
            cal.get(Calendar.MONTH) shouldBe 3
            cal.get(Calendar.YEAR) shouldBe 2020
            cal.get(Calendar.HOUR_OF_DAY) shouldBe 12
            cal.get(Calendar.MINUTE) shouldBe 5
            cal.get(Calendar.SECOND) shouldBe 10
        }
    }
    "converting Calendar to AstroDateTime and reverting it should give the Calendar" {
        val calendar = Calendar.getInstance()
        val converter = AstroDateTimeConverter()
        val adt = converter.calendarToAstroDateTime(calendar)
        val convertedCalendar = converter.astroDateTimeToCalendar(adt)
        assertSoftly {
            convertedCalendar.time shouldBe calendar.time
            convertedCalendar.timeZone shouldBe calendar.timeZone
        }
    }
})