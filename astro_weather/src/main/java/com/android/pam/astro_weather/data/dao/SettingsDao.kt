package com.android.pam.astro_weather.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.pam.astro_weather.data.entity.SettingsEntity

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createSettings(settings: SettingsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setSettings(settings: SettingsEntity)

    @Query("SELECT * FROM settings_table")
    fun getSettings(): SettingsEntity

    @Query("SELECT current_location FROM settings_table")
    fun getLocation(): Long?

    @Query("SELECT refresh_frequency FROM settings_table")
    fun getRefreshFrequency(): Double

    @Query("SELECT units FROM settings_table")
    fun getUnits(): Int
}