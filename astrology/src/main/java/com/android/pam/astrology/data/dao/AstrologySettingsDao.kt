package com.android.pam.astrology.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.pam.astrology.data.entity.AstrologySettingsEntity
import com.android.pam.astrology.domain.model.settings.Location
import io.reactivex.Maybe

@Dao
interface AstrologySettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setSettings(settings: AstrologySettingsEntity)

    @Query("SELECT * FROM astrology_settings_table")
    fun getSettings(): Maybe<AstrologySettingsEntity>

    @Query("SELECT location FROM astrology_settings_table")
    fun getLocation(): Maybe<Location>

    @Query("SELECT refresh_frequency FROM astrology_settings_table")
    fun getRefreshFrequency(): Maybe<Double>
}