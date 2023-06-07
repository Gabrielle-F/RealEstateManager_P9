package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchPropertiesUseCase @Inject constructor(private val propertyRepository: PropertyRepository) {

    fun invoke(minPrice : Int, maxPrice : Int, minArea : Int, maxArea : Int, city: String?,
               types : List<String>?, rooms : List<Int>?,
               availability : Boolean?, startDate : String?, endDate : String?, numberOfPictures: List<Int>,
               agentName : String?, school : Boolean, restaurants : Boolean, playground : Boolean,
               supermarket : Boolean, shoppingArea : Boolean, cinema : Boolean) : Flow<List<Property>> {
        return propertyRepository.getSearchProperties(minPrice, maxPrice, minArea, maxArea, city, types, rooms, availability, startDate, endDate, numberOfPictures, agentName, school, restaurants, playground, supermarket, shoppingArea, cinema)
    }
}