package com.openclassrooms.realestatemanager.utils

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openclassrooms.realestatemanager.model.LocalPicture
import java.net.URI
import java.net.URL

class Converters {

    @TypeConverter
    fun fromLatLngToString(latLng: LatLng) : String {
        return "${latLng.latitude},${latLng.longitude}"
    }

    @TypeConverter
    fun fromStringToLatLng(latLngString: String) : LatLng {
        val parts = latLngString.split(",")
        val latitude = parts[0].toDouble()
        val longitude = parts[1].toDouble()
        return LatLng(latitude, longitude)
    }

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

    @TypeConverter
    fun fromUriToUrl(uri : String) : URL {
        val passedUri = URI(uri)
        return passedUri.toURL()
    }
}