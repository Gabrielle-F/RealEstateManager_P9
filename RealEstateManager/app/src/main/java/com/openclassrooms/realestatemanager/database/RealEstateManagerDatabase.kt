package com.openclassrooms.realestatemanager.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.model.Property

@Database(entities = [Property::class, Image::class, Agent::class], version = 1, exportSchema = true)
abstract class RealEstateManagerDatabase : RoomDatabase() {
    abstract fun propertyDao() : PropertyDao
    abstract fun agentDao() : AgentDao
}