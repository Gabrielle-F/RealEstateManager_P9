package com.openclassrooms.realestatemanager.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openclassrooms.realestatemanager.model.LocalPicture

class Converters {

    @TypeConverter
    fun fromImages(localPictures : List<LocalPicture>) : String {
        val gson = Gson()
        return gson.toJson(localPictures)
    }

    @TypeConverter
    fun toImages(json : String) : List<LocalPicture> {
        val type = object : TypeToken<List<LocalPicture>>() {}.type
        val gson = Gson()
        return gson.fromJson(json, type)
    }
}