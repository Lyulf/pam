package com.android.pam.astrology.domain.utils

import android.os.Looper

object ThreadUtils {
    val isMainThread: Boolean
        get() = Looper.myLooper() == Looper.getMainLooper()
}