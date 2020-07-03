package com.android.pam.astro_weather.data.database

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.pam.astro_weather.data.converter.CoordinatesConverter
import com.android.pam.astro_weather.data.converter.WeatherConverter
import com.android.pam.astro_weather.data.dao.CityDao
import com.android.pam.astro_weather.data.dao.SettingsDao
import com.android.pam.astro_weather.data.dao.WeatherDao
import com.android.pam.astro_weather.data.entity.CityEntity
import com.android.pam.astro_weather.data.entity.SettingsEntity
import com.android.pam.astro_weather.data.entity.WeatherEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

@Database(
    entities = [
        SettingsEntity::class,
        WeatherEntity::class,
        CityEntity::class
        ],
version = 1,
    exportSchema = true
)
@TypeConverters(
    value = [
        CoordinatesConverter::class,
        WeatherConverter::class])
abstract class AstroWeatherRoomDatabase : RoomDatabase() {
    abstract fun astrologySettingsDao(): SettingsDao
    abstract fun weatherDao(): WeatherDao
    abstract fun cityDao(): CityDao

    suspend fun initialize(context: Context) {
        val settingsDao = astrologySettingsDao()
        val cityDao = cityDao()

        settingsDao.createSettings(
            SettingsEntity(
                null,
                1.0,
                1
            )
        )

        var settings = settingsDao.getSettings()
        if(settings.refreshDatabase) {
            withContext (Dispatchers.Main) {
                Toast.makeText(context.applicationContext, "Prepopulating database. Please be patient.", Toast.LENGTH_LONG).show()
            }
            Log.d("create_cities", "loading cities")
            val json = this::class.java.getResource("/res/raw/cities.json")!!.readText()
            Log.d("create_cities", "parsing cities")
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val listType = Types.newParameterizedType(List::class.java, CityEntity::class.java)
            val adapter = moshi.adapter<List<CityEntity>>(listType)
            val cities = adapter.fromJson(json)!!
            Log.d("create_cities", "inserting cities")
            cityDao.createCities(cities)
            Log.d("create_cities", "complete")
            MUTEX.withLock {
                settings = settingsDao.getSettings()
                settings.refreshDatabase = false
                settingsDao.setSettings(settings)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AstroWeatherRoomDatabase? = null
        private val MUTEX = Mutex()

        fun getDatabase(context: Context): AstroWeatherRoomDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): AstroWeatherRoomDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                AstroWeatherRoomDatabase::class.java,
                    "pam_astro_weather_database"
            ).build()
    }

}
