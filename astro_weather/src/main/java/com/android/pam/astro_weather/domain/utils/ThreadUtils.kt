package com.android.pam.astro_weather.domain.utils

import android.os.Looper

object ThreadUtils {
    val isMainThread: Boolean
        get() = Looper.myLooper() == Looper.getMainLooper()
}