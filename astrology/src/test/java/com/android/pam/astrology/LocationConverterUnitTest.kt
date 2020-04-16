package com.android.pam.astrology

import com.android.pam.astrology.data.converter.LocationConverter
import com.android.pam.astrology.domain.model.settings.Location
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LocationConverterUnitTest : StringSpec({
    "locationToJson should convert Location to String" {
        val converter = LocationConverter()
        val location = Location(10.0, 20.0)
        converter.locationToJson(location) shouldBe """{"latitude":10.0,"longitude":20.0}"""
    }
    "locationFromJson should convert String to Location" {
        val converter = LocationConverter()
        val json = """{"latitude":10.0,"longitude":20.0}"""
        val location = converter.locationFromJson(json)
        assertSoftly {
            location.latitude shouldBe 10.0
            location.longitude shouldBe 20.0
        }
    }
})