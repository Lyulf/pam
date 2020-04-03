package com.android.calculator.data

import com.android.calculator.data.common.*
import com.android.calculator.domain.entity.Expression
import com.android.calculator.domain.repository.ICalculate
import java.util.*

class CalculateImpl(private val validator: IValidate) : ICalculate {
    override fun evaluate(expression: Expression): String {
        validator.validateExpression(expression)
        return evaluateInfix(expression.value).toString()
    }

    private fun Char.calcPrecedence(): Int = when {
        this == '_' -> Int.MAX_VALUE
        else -> this.precedence()
    }

    private fun evaluateInfix(tokens: String): Double {
        if(tokens.isBlank()) {
            return 0.0
        }
        val values = Stack<Double>()
        val operators = Stack<Char>()
        var isUnaryOperator = true
        // substitute for
        var it = -1
        while (++it < tokens.length) {
            if (tokens[it].isWhitespace()) {
                continue
            } else if(tokens[it] == '(') {
                operators.push('(')
                isUnaryOperator = true
            } else if(tokens[it] == ')') {
                while(operators.peek() != '(') {
                    applyOp(values, operators)
                }
                operators.pop()
                isUnaryOperator = false
            } else if (tokens[it].isDigit()) {
                val start = it
                var token = tokens.getOrNull(it + 1)
                while(token != null && (token.isDigit() || token == '.')) {
                    token = tokens.getOrNull(++it + 1)
                }
                val value = tokens.substring(start, it + 1).toDouble()
                values.push(value)
                isUnaryOperator = false
            } else if(tokens[it].isOperator()) {
                var op = tokens[it]
                if(isUnaryOperator) {
                    if(op == '+') {
                        continue
                    } else if(op == '-') {
                        op = '_'
                    }
                }
                // opening parentheses has precedence 0
                while(operators.isNotEmpty() && operators.peek().calcPrecedence() >= op.calcPrecedence()) {
                    applyOp(values, operators)
                }
                operators.push(op)
                isUnaryOperator = true
            } else {
                if(tokens[it] == '%') {
                    values.push(values.pop() / 100.0)
                } else {
                    var buffer = ""
                    while(tokens.getOrNull(it)?.isLetter() == true) {
                        buffer += tokens[it++]
                    }
                    if(buffer.isVariable()) {
                        values.push(buffer.asVariable())
                        isUnaryOperator = false
                    } else if(buffer.isMathFunction()){
                        @Suppress("ControlFlowWithEmptyBody")
                        while(tokens[it++] != '(') {}
                        val start = it
                        var openedParentheses = 1
                        while(it < tokens.length) {
                            if(tokens[it] == ')') {
                                if(--openedParentheses == 0) {
                                    break
                                }
                            } else if(tokens[it] == '(') {
                                ++openedParentheses
                            }
                            it++
                        }
                        val valueInside = evaluateInfix(tokens.substring(start, it))
                        val value = buffer.applyMathFunction(valueInside)
                        values.push(value)
                        isUnaryOperator = false
                    }
                }
            }
        }

        while(operators.isNotEmpty()) {
            applyOp(values, operators)
        }

        return values.pop()
    }

    private fun applyOp(values: Stack<Double>, operators: Stack<Char>) {
        val op = operators.pop()
        val b = values.pop()
        val value = if(op == '_') {
            -b
        } else {
            val a = values.pop()
            op.applyOp(a, b)
        }
        values.push(value)
    }
}