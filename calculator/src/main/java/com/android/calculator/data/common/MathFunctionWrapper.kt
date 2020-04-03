package com.android.calculator.data.common

import java.util.*

fun String.isMathFunction(): Boolean = availableMathFunctions[this.toLowerCase(Locale.ROOT)] != null
fun String.applyMathFunction(value: Double): Double = availableMathFunctions[this.toLowerCase(Locale.ROOT)]?.let {
    it.invoke(value)
} ?: 0.0
