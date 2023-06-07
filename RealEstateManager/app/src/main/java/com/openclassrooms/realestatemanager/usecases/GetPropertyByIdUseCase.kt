package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPropertyByIdUseCase @Inject constructor(private val propertyRepository: PropertyRepository) {

    fun invoke(id : String) : Flow<Property>? {
        return propertyRepository.getPropertyById(id)
    }
}