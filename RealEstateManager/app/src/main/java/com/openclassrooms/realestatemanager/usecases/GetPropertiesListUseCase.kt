package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.repository.PropertyRepository
import javax.inject.Inject

class GetPropertiesListUseCase @Inject constructor(private val propertyRepository: PropertyRepository) {

    fun getAllProperties() {
        propertyRepository.getAllProperties()
    }
}