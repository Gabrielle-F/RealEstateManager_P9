package com.openclassrooms.realestatemanager.repository

import android.net.Uri
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.storage.FirebaseStorage
import com.openclassrooms.realestatemanager.database.PropertyDao
import com.openclassrooms.realestatemanager.model.LocalPicture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(private val propertyDao: PropertyDao) {

    private val propertiesCollectionName: String = "properties"
    private val storageRef  = FirebaseStorage.getInstance().reference
    private val picturesStorageRef = storageRef.child("photos/photo_${System.currentTimeMillis()}.jpg")


    private fun getPropertiesCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(propertiesCollectionName)
    }

    fun createPropertyInFirestoreDatabase(property: PropertyFirestore): String {
        val newPropertyRef = getPropertiesCollection().document()
        var createdId = "propertyId"
        if (property != null) {
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
            val pictures = property.pictures
            val numberOfPictures = property.numberOfPictures
            val agentId = property.agentId
            val sold = property.sold
            val soldDate = property.soldDate
            val registerDate = property.registerDate
            val latitude = property.latitude
            val longitude = property.longitude

            val property = PropertyFirestore(
                type,
                price,
                streetName,
                streetNumber,
                area,
                rooms,
                postalCode,
                city,
                sold,
                registerDate,
                soldDate,
                school,
                restaurants,
                playground,
                supermarket,
                shoppingArea,
                cinema,
                pictures,
                numberOfPictures,
                description,
                latitude,
                longitude,
                agentId,
            )
            newPropertyRef.set(property).addOnSuccessListener {
                createdId = newPropertyRef.id
            }
            pictures.forEach {localPicture ->
                val uploadTask = picturesStorageRef.putFile(Uri.parse(localPicture.imageUrl))
                uploadTask.addOnSuccessListener {
                    picturesStorageRef.downloadUrl.addOnSuccessListener {
                        val photoUrl = it.toString()
                        getPropertiesCollection().document(createdId).collection("photos").add(LocalPicture(photoUrl, localPicture.imageTitle, localPicture.imageDescription))
                    }
                }
            }
        }
        return createdId
    }

    suspend fun createProperty(property: Property) = propertyDao.insertProperty(property)

    suspend fun updateProperty(property: Property) = propertyDao.updateProperty(property)

    fun getAllProperties(): Flow<List<Property>> = propertyDao.getAllProperties()

    fun getPropertyById(id: String): Flow<Property>? = propertyDao.getPropertyById(id)

    fun getSearchProperties(
        minPrice: Int, maxPrice: Int, minArea: Int, maxArea: Int, city: String?,
        types: List<String>?, rooms: List<Int>?,
        availability: Boolean?, startDate: String?, endDate: String?, numberOfPictures: List<Int>,
        agentName: String?, school: Boolean, restaurants: Boolean, playground: Boolean,
        supermarket: Boolean, shoppingArea: Boolean, cinema: Boolean
    ): Flow<List<Property>> = propertyDao.getSearchProperties(
        minPrice,
        maxPrice,
        minArea,
        maxArea,
        city,
        types,
        rooms,
        availability,
        startDate,
        endDate,
        numberOfPictures,
        agentName,
        school,
        restaurants,
        playground,
        supermarket,
        shoppingArea,
        cinema
    )

    fun getPropertiesList() : Flow<List<Property>> = flow {
        val propertiesList = mutableListOf<Property>()
        val querySnapshot = getPropertiesCollection().get().await()
        querySnapshot.let {
            it?.let {
                for (document: QueryDocumentSnapshot in it) {
                    val property = document.toObject(Property::class.java)
                    propertiesList.add(property)
                }
            }
        }
        emit(propertiesList)
    }
}