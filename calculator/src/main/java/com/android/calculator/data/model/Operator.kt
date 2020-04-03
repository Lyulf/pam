package com.android.calculator.data.model

data class Operator(
    val precedence: Int,
    val invoke: (Double, Double) -> Double
)
