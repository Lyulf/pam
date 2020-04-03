package com.android.calculator.data

import androidx.annotation.VisibleForTesting
import com.android.calculator.data.common.isMathFunction
import com.android.calculator.data.common.isOperator
import com.android.calculator.data.common.isVariable
import com.android.calculator.data.exception.InvalidExpression
import com.android.calculator.domain.entity.Expression

object ValidateImpl : IValidate {
    /*
    No Invalid characters
    Starts with value, advanced op or variable
    Ends with value, advanced op or variable
    Advanced op has value inside parenthesis
    No double operands or operators
    No opening or closing parenthesis
     */
    override fun validateExpression(expression: Expression) {
        validate(expression.value)
    }

    private fun validate(expression: String) {
        validateCharacters(expression)
        val tokens = splitExpression(expression)
        if(tokens.isEmpty()) {
            return
        }
        validateTokens(tokens)
        validateStart(tokens)
        validateEnd(tokens)
        validateFunctions(tokens)
        validateOperators(tokens)
        validateParenthesis(tokens)
        validatePercent(tokens)
    }

    private fun validateTokens(tokens: List<String>) {
        for(token in tokens) {
            if(token.isNumeric()) {
                continue
            } else if(token == "(" || token == ")") {
                continue
            } else if(token.isOperator()) {
                continue
            } else if(token == "%") {
                continue
            } else if(token.isMathFunction()) {
                continue
            } else if(token.isVariable()) {
                continue
            }
            throw InvalidExpression("Not all tokens are valid")
        }
    }

    private fun String.isNumeric(): Boolean {
        return toDoubleOrNull() != null
    }

    private fun validateStart(tokens: List<String>) {
        val token = tokens.first()
        if((token.isOperator() && token != "-") || token == "%") {
            throw InvalidExpression("Invalid start")
        }
    }

    private fun validateEnd(tokens: List<String>) {
        val token = tokens.last()
        if(token.isOperator()) {
            throw InvalidExpression("Invalid start")
        }
    }

    private fun validateFunctions(tokens: List<String>) {
        for(it in tokens.indices) {
            if(tokens[it].isMathFunction()) {
                val start = it + 1
                if(tokens.getOrNull(start)?.trim() != "(") {
                    throw InvalidExpression("No opening parenthesis in math function")
                }
                val end = indexOfClosingParenthesis(start, tokens)
                if(end == -1) {
                    throw InvalidExpression("No closing parenthesis in math function")
                } else if(end == start + 1) {
                    throw InvalidExpression("Empty math function")
                }
                val ss = StringBuilder()
                for(token in tokens.subList(it + 1, end + 1)) {
                    ss.append(token)
                }
                validate(ss.toString())
            }
        }

    }

    private fun indexOfClosingParenthesis(openingParenthesisPos: Int, tokens: List<String>): Int {
        var it = if(openingParenthesisPos >= -1) openingParenthesisPos else -1
        var noOpenedParentheses = 1
        while(++it < tokens.size) {
            val token = tokens[it].trim()
            if(token == "(") {
                noOpenedParentheses++
            } else if(token == ")") {
                if(--noOpenedParentheses == 0) {
                    return it
                }
            }

        }
        return -1
    }

    private fun validateCharacters(expression: String) {
        for(c in expression) {
            validateChar(c)
        }
    }

    private fun validateOperators(tokens: List<String>) {
        /*
        binary -> left = noOp && left != '('
        number || left_parenthesis -> left = noNumber && left != ')'
        right_parenthesis -> left = number || left = ')'
        mathFunc -> right = '(' || left = noNumber && left != ')'

         */
        for(it in tokens.indices.drop(1).reversed()) {
            val token = tokens[it]
            val tokenBefore = tokens[it - 1]
            if(token.isOperator() && !token.isUnaryOperator()) {
                if(tokenBefore.isOperator() || tokenBefore.contains('(')) {
                    throw InvalidExpression("Invalid operator")
                }
            } else if(token.isNumber() || token.contains('(')) {
                when {
                    tokenBefore.isNumber() -> {
                        throw InvalidExpression("Invalid operand")
                    }
                    tokenBefore.contains(')') -> {
                        throw InvalidExpression("Invalid parenthesis")
                    }
                    tokenBefore.contains('%') -> {
                        throw InvalidExpression("Invalid percent")
                    }
                }
            } else if(token.contains(')')) {
                if(tokenBefore.isOperator()) {
                    throw InvalidExpression("Operator before closing parenthesis")
                } else if(tokenBefore.contains('(')) {
                    throw InvalidExpression("Empty parenthesis")
                }
            } else if(token.isMathFunction()) {
                val tokenAfter = tokens.getOrNull(it + 1)
                validateMathFunction(tokenAfter, tokenBefore)
            }
        }
        val firstToken = tokens.first()
        if(firstToken.isMathFunction()) {
            val tokenAfter = tokens.getOrNull(1)
            validateMathFunction(tokenAfter)
        }
    }

    private fun validateMathFunction(tokenAfter: String?, tokenBefore: String? = null) {
        if(tokenAfter?.contains('(') != true) {
            throw InvalidExpression(
                "No opening parenthesis after mathematical function"
            )
        } else {
            tokenBefore?.let {
                if(it.isNumber()) {
                    throw InvalidExpression("Invalid operand")
                } else if(it.contains(')')) {
                    throw InvalidExpression("Invalid parenthesis")
                } else if(it.contains('%')) {
                    throw InvalidExpression("Invalid percent")
                }
            }
        }
    }

    private fun String.isOperator(): Boolean {
        val token = this.trim()
        return token.length == 1 && token.first().isOperator()
    }

    private fun String.isUnaryOperator(): Boolean {
        val token = this.trim()
        return token.length == 1 && (token.contains('-') || token.contains('+'))
    }

    private fun String.isNumber(): Boolean {
        val token = this.trim()
        return token.isNumeric() || token.isVariable()
    }

    private fun validateParenthesis(tokens: List<String>) {
        var openedParentheses = 0
        for(token in tokens) {
            if(token.contains(')')) {
                if(--openedParentheses < 0) {
                    throw InvalidExpression("Close parenthesis without open parenthesis")
                }
            } else if(token.contains('(')) {
                openedParentheses++
            }
        }
        if(openedParentheses != 0) {
            throw InvalidExpression("Not every parenthesis is closed ($openedParentheses)")
        }
    }

    private fun validatePercent(tokens: List<String>) {
        for(it in tokens.indices.drop(1).reversed()) {
            if(tokens[it].contains('%')) {
                if(!tokens[it - 1].isNumber()) {
                    throw InvalidExpression("Percent without operand")
                }
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun splitExpression(expression: String): List<String> {
        val tokens = ArrayList<String>()
        var start = 0
        for(it in expression.indices) {
            val token = expression[it]
            if(token.isOperator() || token.isParenthesis() || token == '%') {
                val temp = expression.substring(start, it).trim()
                if(temp.isNotEmpty()) {
                    tokens.add(temp)
                }
                tokens.add(expression[it].toString())
                start = it + 1
            }
        }
        val lastToken = expression.substring(start).trim()
        if(lastToken.isNotEmpty()) {
            tokens.add(lastToken)
        }
        return tokens
    }

    private fun Char.isParenthesis(): Boolean {
        return this == '(' || this == ')'
    }

    private fun validateChar(c: Char) {
        when {
            c.isWhitespace() -> return
            c.isLetterOrDigit() -> return
            c.isParenthesis() -> return
            c.isOperator() -> return
            c == '%' -> return
            else -> throw InvalidExpression("Invalid character")
        }
    }
}