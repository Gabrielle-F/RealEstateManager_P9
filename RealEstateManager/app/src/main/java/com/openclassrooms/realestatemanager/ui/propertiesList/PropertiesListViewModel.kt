package com.openclassrooms.realestatemanager.ui.propertiesList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.usecases.GetPropertiesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PropertiesListViewModel @Inject constructor(private val getAllPropertiesListUseCase: GetPropertiesListUseCase) : ViewModel() {

    val propertiesLiveData = object : MutableLiveData<List<Property>>(){}

    suspend fun getProperties() {
        getAllPropertiesListUseCase.invoke().collect {
            properties -> propertiesLiveData.postValue(properties)
        }
    }
}