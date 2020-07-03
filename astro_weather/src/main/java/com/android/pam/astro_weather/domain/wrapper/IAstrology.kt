package com.android.pam.astro_weather.domain.wrapper

import com.android.pam.astro_weather.domain.model.moon.Moon
import com.android.pam.astro_weather.domain.model.sun.Sun

interface IAstrology {
    fun refresh()
    suspend fun updateSettings()
    fun moon(): Moon
    fun sun(): Sun
}