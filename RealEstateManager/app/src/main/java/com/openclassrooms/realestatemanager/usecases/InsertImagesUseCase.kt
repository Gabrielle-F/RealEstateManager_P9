package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.repository.ImageRepository
import javax.inject.Inject

class InsertImagesUseCase @Inject constructor(private val imageRepository: ImageRepository) {

    suspend fun execute(image : Image) = imageRepository.insertImageInLocalDatabase(image)
}