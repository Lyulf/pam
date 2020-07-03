package com.android.pam.astro_weather.domain.utils

import androidx.lifecycle.MutableLiveData

object LiveDataUtils {
    fun <T>MutableLiveData<T>.update(newValue: T?) {
        when(ThreadUtils.isMainThread) {
            true -> value = newValue
            else -> postValue(newValue)
        }
    }

    var <T>MutableLiveData<T>.newValue: T?
        get() = value
        set(value) = this.update(value)
}