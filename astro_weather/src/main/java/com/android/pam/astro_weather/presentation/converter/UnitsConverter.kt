package com.android.pam.astro_weather.presentation.converter

import com.android.pam.astro_weather.domain.model.settings.Settings

class UnitsConverter(var units: Settings.Units) {
    fun temperature(value: Float?): String? {
        if(value == null) {
            return null
        }
        return when(units) {
            Settings.Units.STANDARD -> "$%.0fK".format(value)
            Settings.Units.METRIC -> "%.0f°C".format(value - 273.1)
            Settings.Units.IMPERIAL -> "%.0f°F".format((value - 273.1) * 9 / 5 + 32)
        }
    }

}