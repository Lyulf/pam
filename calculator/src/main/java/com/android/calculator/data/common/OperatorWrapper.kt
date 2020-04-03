package com.android.calculator.data.common

fun Char.isOperator(): Boolean = availableOperators[this] != null
fun Char.precedence(): Int = availableOperators[this]?.precedence ?: 0
fun Char.applyOp(a: Double, b: Double): Double = availableOperators[this]?.let {
    it.invoke(a,b)
} ?: 0.0
