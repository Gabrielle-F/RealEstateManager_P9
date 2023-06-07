package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.PropertyFirestore
import com.openclassrooms.realestatemanager.repository.PropertyRepository
import javax.inject.Inject

class CreatePropertyInFirestoreUseCase @Inject constructor(private val propertyRepository: PropertyRepository) {

    fun invoke(property: PropertyFirestore): String = propertyRepository.createPropertyInFirestoreDatabase(property)
}