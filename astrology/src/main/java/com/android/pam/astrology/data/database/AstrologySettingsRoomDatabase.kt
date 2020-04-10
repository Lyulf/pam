package com.android.pam.astrology.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.pam.astrology.data.converter.LocationConverter
import com.android.pam.astrology.data.dao.AstrologySettingsDao
import com.android.pam.astrology.data.entity.AstrologySettingsEntity

@Database(
    entities = [AstrologySettingsEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(value = [LocationConverter::class])
abstract class AstrologySettingsRoomDatabase : RoomDatabase() {
    abstract fun astrologySettingsDao(): AstrologySettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AstrologySettingsRoomDatabase? = null

        fun getDatabase(context: Context): AstrologySettingsRoomDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AstrologySettingsRoomDatabase::class.java,
                    "expression_database"
                ).build()
                return INSTANCE!!
            }
        }
    }
}
