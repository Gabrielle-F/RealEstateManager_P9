package com.openclassrooms.realestatemanager.database

import androidx.room.Database
import com.openclassrooms.realestatemanager.model.Property

@Database(entities = [Property::class], version = 1, exportSchema = false)
abstract class RealEstateManagerDatabase {

    abstract fun propertyDao() : PropertyDao

}