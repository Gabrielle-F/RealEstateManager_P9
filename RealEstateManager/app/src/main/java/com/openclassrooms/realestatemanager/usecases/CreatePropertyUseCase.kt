package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PropertyRepository
import javax.inject.Inject

class CreatePropertyUseCase @Inject constructor(private val propertyRepository: PropertyRepository) {
    fun createProperty(property: Property) = propertyRepository.createProperty(property)
}