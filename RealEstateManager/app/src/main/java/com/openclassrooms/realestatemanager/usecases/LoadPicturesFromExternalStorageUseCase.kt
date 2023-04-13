package com.openclassrooms.realestatemanager.usecases

import android.content.Context
import com.openclassrooms.realestatemanager.repository.ImageRepository
import javax.inject.Inject

class LoadPicturesFromExternalStorageUseCase @Inject constructor(private val imageRepository : ImageRepository){

    suspend fun execute(context: Context) = imageRepository.loadPicturesFromExternalStorage(context)
}