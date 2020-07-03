package com.android.pam.astro_weather.domain.model.location

data class City(
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val coordinates: Coordinates,
    var favourite: Boolean
)