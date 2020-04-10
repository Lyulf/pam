package com.android.pam.astrology.data.converter

import androidx.room.TypeConverter
import com.android.pam.astrology.domain.model.settings.Location
import com.google.gson.Gson

class LocationConverter {
    @TypeConverter
    fun locationToJson(location: Location): String {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun locationFromJson(json: String): Location {
        return Gson().fromJson(json, Location::class.java)
    }
}