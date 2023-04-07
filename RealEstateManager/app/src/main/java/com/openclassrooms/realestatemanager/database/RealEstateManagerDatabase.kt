package com.openclassrooms.realestatemanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.model.Property

@Database(entities = [Property::class, Image::class], version = 1, exportSchema = false)
abstract class RealEstateManagerDatabase : RoomDatabase() {
    abstract fun propertyDao() : PropertyDao

}