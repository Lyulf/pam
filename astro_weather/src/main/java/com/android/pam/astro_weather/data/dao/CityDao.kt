package com.android.pam.astro_weather.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.pam.astro_weather.data.entity.CityEntity

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createCities(cities: List<CityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityEntity>)

    @Query("SELECT COUNT(id) FROM city_table")
    suspend fun getNumberOfCities(): Long

    @Query("SELECT * FROM city_table WHERE id = :id")
    suspend fun getCity(id: Long): CityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCity(city: CityEntity)

    @Query(
        "SELECT * FROM city_table " +
        "WHERE (:id IS NULL OR CAST(id as CHAR) LIKE CAST(:id as CHAR) || '%') " +
        "AND (:name IS NULL OR UPPER(name) LIKE UPPER(:name || '%')) " +
        "AND (:state IS NULL OR UPPER(state) LIKE UPPER(:state || '%')) " +
        "AND (:country IS NULL OR UPPER(country) LIKE UPPER(:country || '%')) " +
        "AND (:favourite IS NULL OR favourite = :favourite) ")
    suspend fun getCities(
        name: String? = null,
        state: String? = null,
        country: String? = null,
        favourite: Boolean? = null,
        id: Long? = null
    ): List<CityEntity>

    @Query("SELECT * FROM city_table WHERE favourite = 1")
    suspend fun getFavourites(): List<CityEntity>

    @Query("SELECT * FROM city_table WHERE id in (:ids)")
    suspend fun getCitiesByIds(ids: List<Long>): List<CityEntity>
}