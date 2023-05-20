package com.openclassrooms.realestatemanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.utils.Converters

@Database(entities = [Property::class, Image::class, Agent::class, Client::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RealEstateManagerDatabase : RoomDatabase() {
    abstract fun propertyDao() : PropertyDao
    abstract fun agentDao() : AgentDao
    abstract fun clientDao() : ClientDao
    abstract fun imageDao() : ImageDao
}