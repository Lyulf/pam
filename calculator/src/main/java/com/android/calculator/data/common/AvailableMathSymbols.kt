package com.android.calculator.data.common

import com.android.calculator.data.model.MathFunction
import com.android.calculator.data.model.Operator
import com.android.calculator.data.model.Variable
import kotlin.math.ln

val availableMathFunctions = hashMapOf<String, MathFunction> (
    "sin" to MathFunction{ x -> StrictMath.sin(x) },
    "cos" to MathFunction{ x -> StrictMath.cos(x) },
    "tan" to MathFunction{ x -> StrictMath.tan(x) },
    "sqrt" to MathFunction{ x -> StrictMath.sqrt(x) },
    "log" to MathFunction{ x -> StrictMath.log10(x) },
    "ln" to MathFunction{ x -> ln(x) }
)

val availableOperators = hashMapOf<Char, Operator>(
    '+' to Operator(1) { a: Double, b: Double -> a + b },
    '-' to Operator(1) { a: Double, b: Double -> a - b },
    '*' to Operator(2) { a: Double, b: Double -> a * b },
    '/' to Operator(2) { a: Double, b: Double -> a / b },
    '^' to Operator( 3) { a: Double, b: Double -> StrictMath.pow(a, b) }
)

val availableVariables = hashMapOf<String, Variable>(
    "pi" to Variable(Math.PI)
)
