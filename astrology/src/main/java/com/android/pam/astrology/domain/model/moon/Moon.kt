package com.android.pam.astrology.domain.model.moon

data class Moon(
    val moonrise: Moonrise,
    val moonset: Moonset,
    val nextNewMoon: NewMoon,
    val nextFullMoon: FullMoon,
    val moonPhase: MoonPhase,
    val dayOfSynodicMonth: Int
)