package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPropertiesListUseCase @Inject constructor(private val propertyRepository: PropertyRepository) {

    fun invoke() : Flow<List<Property>> {
        return propertyRepository.getAllProperties()
    }

}