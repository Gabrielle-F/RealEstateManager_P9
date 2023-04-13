package com.openclassrooms.realestatemanager.ui.addProperty

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.ExternalStorageImage
import com.openclassrooms.realestatemanager.usecases.LoadPicturesFromExternalStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPicturesViewModel @Inject constructor(private val loadPicturesFromExternalStorageUseCase: LoadPicturesFromExternalStorageUseCase) : ViewModel() {

    val imagesLiveData = object : MutableLiveData<List<ExternalStorageImage>>(){}

    suspend fun getImages(context : Context) {
        val imagesList : List<ExternalStorageImage> = loadPicturesFromExternalStorageUseCase.execute(context)
        imagesLiveData.postValue(imagesList)
    }
}
