package com.android.calculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.calculator.data.model.ExpressionModel
import com.android.calculator.domain.entity.Expression

@Dao
interface ExpressionDao {
    @Query("SELECT expression FROM expression_table")
    fun getExpression(): Expression

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expression: ExpressionModel)

    @Query("DELETE FROM expression_table")
    suspend fun deleteAll()
}