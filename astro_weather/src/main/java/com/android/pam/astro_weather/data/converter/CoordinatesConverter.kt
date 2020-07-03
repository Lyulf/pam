package com.android.pam.astro_weather.data.converter

import androidx.room.TypeConverter
import com.android.pam.astro_weather.data.entity.CoordinatesEntity
import com.android.pam.astro_weather.domain.model.location.Coordinates
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object CoordinatesConverter {
//    fun coordsEntityToJson(entity: CoordinatesEntity): String {
//        return entity.toJson()
//    }
//
//    fun jsonToCoordsEntity(json: String): CoordinatesEntity? {
//        return json.toCoordinatesEntity()
//    }

    @TypeConverter
    @JvmStatic
    fun CoordinatesEntity.toJson(): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(CoordinatesEntity::class.java)
        return adapter.toJson(this)
    }

    @TypeConverter
    @JvmStatic
    fun String.toCoordinatesEntity(): CoordinatesEntity? {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(CoordinatesEntity::class.java)
        return adapter.fromJson(this)
    }

    @JvmStatic
    fun CoordinatesEntity.toCoordinates(): Coordinates {
        return Coordinates(lon, lat)
    }

    @JvmStatic
    fun Coordinates.toCoordinatesEntity(): CoordinatesEntity {
        return CoordinatesEntity(longitude, latitude)
    }
}