package com.android.calculator.data.mapper

import androidx.room.TypeConverter
import com.android.calculator.data.model.ExpressionModel
import com.android.calculator.domain.entity.Expression

object ExpressionMapper {
    @TypeConverter
    @JvmStatic
    fun fromExpression(expression: Expression): String = expression.value

    @TypeConverter
    @JvmStatic
    fun toExpression(value: String): Expression = Expression(value)

    @TypeConverter
    @JvmStatic
    fun mapFromExpression(from: Expression): ExpressionModel = ExpressionModel(from)

    @TypeConverter
    @JvmStatic
    fun mapToExpression(from: ExpressionModel): Expression = from.expression
}