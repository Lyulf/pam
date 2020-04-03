package com.android.calculator.data.common

import java.util.*

fun String.isVariable(): Boolean = availableVariables[this.toLowerCase(Locale.ROOT)]?.value != null
fun String.asVariable(): Double = availableVariables[this.toLowerCase(Locale.ROOT)]?.value ?: 0.0
