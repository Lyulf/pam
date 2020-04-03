package com.android.calculator

import com.android.calculator.data.ValidateImpl
import com.android.calculator.data.exception.InvalidExpression
import com.android.calculator.domain.entity.Expression
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidatorImplTest {
    private val validator = ValidateImpl

    private fun splitToString(expression: String): String {
        val result = validator.splitExpression(expression)
        return result.joinToString(",", "[", "]")
    }

    @Test
    fun splitTest_isCorrect() {
        val expression = "10 + 10"
        val result = splitToString(expression)
        assertEquals("[10,+,10]", result)
    }

    @Test
    fun splitEmptyTest_isCorrect() {
        val expression = ""
        val result = splitToString(expression)
        assertEquals("[]", result)
    }

    @Test
    fun splitDoublePlusTest_isCorrect() {
        val expression = "10 ++ 10"
        val result = splitToString(expression)
        assertEquals("[10,+,+,10]", result)
    }

    @Test
    fun splitSinTest_isCorrect() {
        val expression = "sin(10)"
        val result = splitToString(expression)
        assertEquals("[sin,(,10,)]", result)
    }

    @Test
    fun splitComplexExpressionTest_isCorrect() {
        val expression = "  sin((Pi / 2) + Pi / 2) + 10 * (sqrt(100) + 3)"
        val result = splitToString(expression)
        assertEquals("[sin,(,(,Pi,/,2,),+,Pi,/,2,),+,10,*,(,sqrt,(,100,),+,3,)]", result)
    }

    @Test
    fun onlyNumberTest_isValid() {
        val expression = Expression("10")
        validator.validateExpression(expression)
    }

    @Test
    fun additionTest_isValid() {
        val expression = Expression("10 + 10")
        validator.validateExpression(expression)
    }

    @Test
    fun substitutionTest_isValid() {
        val expression = Expression("10 - 10")
        validator.validateExpression(expression)
    }

    @Test
    fun multiplicationTest_isValid() {
        val expression = Expression("10 * 10")
        validator.validateExpression(expression)
    }

    @Test
    fun divisionTest_isValid() {
        val expression = Expression("10 / 10")
        validator.validateExpression(expression)
    }

    @Test
    fun powerTest_isValid() {
        val expression = Expression("10^2")
        validator.validateExpression(expression)
    }

    @Test
    fun percentTest_isValid() {
        val expression = Expression("10%")
        validator.validateExpression(expression)
    }

    @Test
    fun piTest_isValid() {
        val expression = Expression("Pi")
        validator.validateExpression(expression)
    }

    @Test
    fun sinTest_isValid() {
        val expression = Expression("sin(Pi)")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun unaryMinusBetweenSinAndParenthesisTest_isInvalid() {
        val expression = Expression("sin-(10)")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun numberBetweenSinAndParenthesisTest_isInvalid() {
        val expression = Expression("sin1(0)")
        validator.validateExpression(expression)
    }

    @Test
    fun additionBeforeParenthesisTest_isValid() {
        val expression = Expression("7 + (7)")
        validator.validateExpression(expression)
    }

    @Test
    fun binaryOperatorBeforeUnaryPlusTest_isValid() {
        val expression = Expression("7 * + 7")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun consecutiveOperatorTest_isInvalid() {
        val expression = Expression("7 * * 7")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun additionBeforeMultiplicationTest_isInvalid() {
        val expression = Expression("7 + * 7")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun closingParenthesisBetweenOperatorAndOperandTest_isInvalid() {
        val expression = Expression("(7 + ) 7")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun openingParenthesisBetweenOperandAndOperatorTest_isInvalid() {
        val expression = Expression("7 ( + 7)")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun operatorAfterOpeningParenthesisTest_isInvalid() {
        val expression = Expression("( * 7)")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun operatorBeforeClosingParenthesisTest_isInvalid() {
        val expression = Expression("(7 + )")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun consecutiveOperandsTest_isInvalid() {
        val expression = Expression("7 7")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun consecutiveOperandsWithFirstOneInParenthesisTest_isInvalid() {
        val expression = Expression("(7) 7")
        validator.validateExpression(expression)
    }

    @Test
    fun cosTest_isValid() {
        val expression = Expression("cos(Pi)")
        validator.validateExpression(expression)
    }

    @Test
    fun tanTest_isValid() {
        val expression = Expression("tan(Pi)")
        validator.validateExpression(expression)
    }

    @Test
    fun sqrtTest_isValid() {
        val expression = Expression("sqrt(Pi)")
        validator.validateExpression(expression)
    }

    @Test
    fun parenthesisTest_isValid() {
        val expression = Expression("(20)")
        validator.validateExpression(expression)
    }

    @Test
    fun logTest_isValid() {
        val expression = Expression("log(10)")
        validator.validateExpression(expression)
    }

    @Test
    fun lnTest_isValid() {
        val expression = Expression("ln(10)")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun doubleOperatorTest_isInvalid() {
        val expression = Expression("10**10")
        validator.validateExpression(expression)
    }

    @Test
    fun subtractionWithUnaryMinusTest_isValid() {
        val expression = Expression("10--10")
        validator.validateExpression(expression)
    }

    @Test
    fun multiplicationWithUnaryMinusTest_isValid() {
        val expression = Expression("10*-10")
        validator.validateExpression(expression)
    }

    @Test
    fun complexExpressionTest_isValid() {
        val expression = Expression("  sin((Pi / 2) + Pi / 2) + 10 * (sqrt(100) + 3)")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun percentAtStartTest_isInvalid() {
        val expression = Expression("%20")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun percentAsOperandTest_isInvalid() {
        val expression = Expression("2 % 20")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun percentBeforeOperandTest_isInvalid() {
        val expression = Expression("2 + %20")
        validator.validateExpression(expression)
    }

    @Test
    fun unaryMinusAtStartTest_isValid() {
        val expression = Expression("-20")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun subtractionAtEndTest_isInvalid() {
        val expression = Expression("20-")
        validator.validateExpression(expression)
    }

    @Test
    fun whiteSpaceTest_isValid() {
        val expression = Expression("   \n  ")
        validator.validateExpression(expression)
    }

    @Test
    fun emptyExpressionTest_isValid() {
        val expression = Expression("")
        validator.validateExpression(expression)
    }

    @Test
    fun multipleParenthesesTest_isValid() {
        val expression = Expression("(((20) )+ (10))")
        validator.validateExpression(expression)
    }

    @Test
    fun negationBeforeParenthesesTest_isValid() {
        val expression = Expression("-(20)")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun emptyParenthesesTest_isInValid() {
        val expression = Expression("( )")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun onlyLeftParenthesisTest_isInvalid() {
        val expression = Expression("(20")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun onlyRightParenthesisTest_isInvalid() {
        val expression = Expression("20)")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun operatorInsideParenthesesTest_isInvalid() {
        val expression = Expression("10 (+) 10")
        validator.validateExpression(expression)
    }

    @Test(expected = InvalidExpression::class)
    fun unaryPlusWithoutOperandInsideParenthesesTest_isInvalid() {
        val expression = Expression("(+)")
        validator.validateExpression(expression)
    }
}
