package com.openclassrooms.realestatemanager.utils

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openclassrooms.realestatemanager.model.Image

class Converters {

    fun convertStringToUri(stringValue: String) : Uri {
        val uriToReturn = Uri.parse(stringValue)
        return uriToReturn
    }

    @TypeConverter
    fun fromImages(images : List<Image>) : String {
        val gson = Gson()
        return gson.toJson(images)
    }

    @TypeConverter
    fun toImages(json : String) : List<Image> {
        val type = object : TypeToken<List<Image>>() {}.type
        val gson = Gson()
        return gson.fromJson(json, type)
    }
}