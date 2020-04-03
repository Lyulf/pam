package com.android.calculator.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.calculator.domain.entity.Expression

@Entity(tableName = "expression_table")
data class ExpressionModel(
    @ColumnInfo(name = "expression")
    val expression: Expression,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0
)
