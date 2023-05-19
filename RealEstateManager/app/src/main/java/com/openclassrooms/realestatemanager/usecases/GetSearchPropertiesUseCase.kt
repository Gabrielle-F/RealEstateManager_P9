package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchPropertiesUseCase @Inject constructor(private val propertyRepository: PropertyRepository) {

    fun invoke(minPrice : Int, maxPrice : Int, minArea : Int, maxArea : Int,
               types : List<String>?, rooms : List<Int>?,
               availability : Boolean?, startDate : String?, endDate : String?,
               agentId : Int?, school : Boolean, restaurants : Boolean, playground : Boolean,
               supermarket : Boolean, shoppingArea : Boolean, cinema : Boolean) : Flow<List<Property>> {
        return propertyRepository.getSearchProperties(minPrice, maxPrice, minArea, maxArea, types, rooms, availability, startDate, endDate, agentId, school, restaurants, playground, supermarket, shoppingArea, cinema)
    }
}