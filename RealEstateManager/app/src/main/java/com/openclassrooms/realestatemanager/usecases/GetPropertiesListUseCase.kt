package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetPropertiesListUseCase @Inject constructor(private val propertyRepository: PropertyRepository) {

    fun invoke(): Flow<List<Property>> {
        return propertyRepository.getPropertiesList().onEach {
            for (property: Property in it) {
                propertyRepository.createProperty(property)
            }
        }
    }
}