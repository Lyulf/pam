package com.android.pam.astrology.data.database

import androidx.room.Database
import com.android.pam.astrology.data.entity.AstrologySettingsEntity

@Database(
    entities = [AstrologySettingsEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AstrologySettingsRoomDatabase