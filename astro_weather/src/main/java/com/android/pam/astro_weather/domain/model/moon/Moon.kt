package com.android.pam.astro_weather.domain.model.moon

data class Moon(
    val moonrise: Moonrise = Moonrise(),
    val moonset: Moonset = Moonset(),
    val nextNewMoon: NewMoon = NewMoon(),
    val nextFullMoon: FullMoon = FullMoon(),
    val moonPhase: MoonPhase = MoonPhase(),
    val dayOfSynodicMonth: Int? = null
)