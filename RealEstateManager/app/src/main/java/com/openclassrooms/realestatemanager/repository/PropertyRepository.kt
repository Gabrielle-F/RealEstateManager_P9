package com.openclassrooms.realestatemanager.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.openclassrooms.realestatemanager.database.PropertyDao
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(private val propertyDao : PropertyDao) {

    private val propertiesCollectionName : String = "properties"

    private fun getPropertiesCollection() : CollectionReference {
        return FirebaseFirestore.getInstance().collection(propertiesCollectionName)
    }

    suspend fun createPropertyInFirestoreDatabase(property: PropertyFirestore) {
        if(property != null) {
            val id = property.id
            val type = property.type
            val price = property.price
            val streetName = property.streetName
            val streetNumber = property.streetNumber
            val area = property.area
            val city = property.city
            val rooms = property.rooms
            val postalCode = property.postalCode
            val description = property.description
            val school = property.school
            val restaurants = property.restaurants
            val playground = property.playground
            val supermarket = property.supermarket
            val shoppingArea = property.shoppingArea
            val cinema = property.cinema
            val numberOfPictures = property.numberOfPictures
            val agentId = property.agentId
            val sold = property.sold
            val soldDate = property.soldDate
            val registerDate = property.registerDate
            val latLng = property.latLng
            val pictures = property.picturesUri
            val agentName = property.agentName

            val property = PropertyFirestore(id, type, price, streetName, streetNumber, area, rooms,
                postalCode, city, sold, registerDate, soldDate, school, restaurants, playground,
                supermarket, shoppingArea, cinema, pictures, numberOfPictures, description, latLng, agentId, agentName)
            getPropertiesCollection().document(id).set(property)
        }
    }

    suspend fun createProperty(property : Property) = propertyDao.insertProperty(property)

    suspend fun updateProperty(property: Property) = propertyDao.updateProperty(property)

    fun getAllProperties(): Flow<List<Property>> = propertyDao.getAllProperties()

    fun getPropertyById(id : Int) : Flow<Property>? = propertyDao.getPropertyById(id)

    fun getSearchProperties(minPrice : Int, maxPrice : Int, minArea : Int, maxArea : Int, city: String?,
                            types : List<String>?, rooms : List<Int>?,
                            availability : Boolean?, startDate : String?, endDate : String?, numberOfPictures: List<Int>,
                            agentId : Int?, school : Boolean, restaurants : Boolean, playground : Boolean,
                            supermarket : Boolean, shoppingArea : Boolean, cinema : Boolean) : Flow<List<Property>> = propertyDao.getSearchProperties(minPrice, maxPrice, minArea, maxArea, city, types, rooms, availability, startDate, endDate, numberOfPictures, agentId, school, restaurants, playground, supermarket, shoppingArea, cinema)

}