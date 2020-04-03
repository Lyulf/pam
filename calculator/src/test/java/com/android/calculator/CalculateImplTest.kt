package com.android.calculator

import com.android.calculator.data.CalculateImpl
import com.android.calculator.data.IValidate
import com.android.calculator.data.ValidateImpl
import com.android.calculator.domain.entity.Expression
import com.android.calculator.domain.repository.ICalculate
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.StrictMath.sin

class CalculateImplTest {
    private val validator: IValidate = ValidateImpl
    private val calculator: ICalculate = CalculateImpl(validator)

    @Test
    fun percentTest_isCorrect() {
        val expression = Expression("20%")
        val result = calculator.evaluate(expression)
        assertEquals("0.2", result)
    }

    @Test
    fun variableTest_isCorrect() {
        val expression = Expression("pi")
        val result = calculator.evaluate(expression)
        assertEquals(Math.PI.toString(), result)
    }
    @Test
    fun sinTest_isCorrect() {
        val expression = Expression("sin(Pi)")
        val result = calculator.evaluate(expression)
        assertEquals(sin(Math.PI).toString(), result)
    }

    @Test
    fun negativeNumberTest_isCorrect() {
        val expression = Expression("- 20")
        val result = calculator.evaluate(expression)
        assertEquals("-20.0", result)
    }

    @Test
    fun multipleNegativesTest_isCorrect() {
        // [_,(,_,20,-,_,50,)]
        val expression = Expression("-(-20 - -50)")
        val result = calculator.evaluate(expression)
        assertEquals("-30.0", result)
    }

    @Test
    fun multipleParenthesesNegationTest_isCorrect() {
        val expression = Expression("-(((-(20)-(-10))))")
        val result = calculator.evaluate(expression)
        assertEquals("10.0", result)
    }

    @Test
    fun complexExpressionTest_isCorrect() {
        val expression = Expression("  sin((Pi / 2) + Pi / 2) + 10 * (sqrt(100) + 3)")
        val result = calculator.evaluate(expression)
        assertEquals("130.0", result)
    }
}