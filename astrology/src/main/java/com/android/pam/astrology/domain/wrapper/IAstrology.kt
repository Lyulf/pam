package com.android.pam.astrology.domain.wrapper

import com.android.pam.astrology.domain.model.moon.Moon
import com.android.pam.astrology.domain.model.sun.Sun

interface IAstrology {
    fun refresh()
    fun updateSettings()
    fun moon(): Moon
    fun sun(): Sun
}