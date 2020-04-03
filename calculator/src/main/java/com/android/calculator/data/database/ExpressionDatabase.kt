package com.android.calculator.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.calculator.data.dao.ExpressionDao
import com.android.calculator.data.mapper.ExpressionMapper
import com.android.calculator.data.model.ExpressionModel

@Database(entities = [ExpressionModel::class], version = 1, exportSchema = false)
@TypeConverters(ExpressionMapper::class)
abstract class ExpressionDatabase : RoomDatabase() {
    abstract fun expressionDao(): ExpressionDao

    companion object {
        @Volatile
        private var INSTANCE: ExpressionDatabase? = null

        fun getDatabase(context: Context): ExpressionDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ExpressionDatabase::class.java,
                    "expression_database"
                ).build()
                return INSTANCE!!
            }
        }
    }
}