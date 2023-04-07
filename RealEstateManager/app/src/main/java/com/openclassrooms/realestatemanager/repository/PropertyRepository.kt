package com.openclassrooms.realestatemanager.repository

import com.openclassrooms.realestatemanager.database.PropertyDao
import com.openclassrooms.realestatemanager.model.Property
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(private val propertyDao : PropertyDao) {

    fun createProperty(property : Property) = propertyDao.insertProperty(property)

    fun getAllProperties(): Flow<List<Property>> = propertyDao.getAllProperties()



}