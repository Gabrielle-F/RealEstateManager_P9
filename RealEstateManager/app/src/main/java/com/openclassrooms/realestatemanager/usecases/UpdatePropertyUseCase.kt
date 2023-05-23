package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PropertyRepository
import javax.inject.Inject

class UpdatePropertyUseCase @Inject constructor(private val propertyRepository: PropertyRepository) {

    suspend fun invoke(property: Property) = propertyRepository.updateProperty(property)
}