package com.openclassrooms.realestatemanager.repository

import com.openclassrooms.realestatemanager.database.PropertyDao
import com.openclassrooms.realestatemanager.model.Property
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(private val propertyDao : PropertyDao) {

    suspend fun createProperty(property : Property) = propertyDao.insertProperty(property)

    fun getAllProperties(): Flow<List<Property>> = propertyDao.getAllProperties()

    fun getPropertyById(id : Int) : Flow<Property> = propertyDao.getPropertyById(id)

    fun getSearchProperties(minPrice : Int, maxPrice : Int, minArea : Int, maxArea : Int, city: String?,
                            types : List<String>?, rooms : List<Int>?,
                            availability : Boolean?, startDate : String?, endDate : String?, numberOfPictures: List<Int>,
                            agentId : Int?, school : Boolean, restaurants : Boolean, playground : Boolean,
                            supermarket : Boolean, shoppingArea : Boolean, cinema : Boolean) : Flow<List<Property>> = propertyDao.getSearchProperties(minPrice, maxPrice, minArea, maxArea, city, types, rooms, availability, startDate, endDate, numberOfPictures, agentId, school, restaurants, playground, supermarket, shoppingArea, cinema)

}