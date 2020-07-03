package com.android.pam.astro_weather.presentation.extensions

import android.widget.ImageView
import androidx.core.content.ContextCompat

object ImageViewExtensions {
    @JvmStatic
    fun ImageView.setImage(name: String, defType: String, defPackage: String) {
        val resId = resources.getIdentifier(name, defType, defPackage)
        val drawable = ContextCompat.getDrawable(context, resId)
        this.setImageDrawable(drawable)
    }
}