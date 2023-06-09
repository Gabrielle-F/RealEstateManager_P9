package com.openclassrooms.realestatemanager.ui.propertiesMap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.usecases.GetPropertiesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val getPropertiesListUseCase: GetPropertiesListUseCase) : ViewModel() {

    val propertiesLiveData = object : MutableLiveData<List<Property>>(){}
    val propertiesLD = object : MutableLiveData<List<Property>>(){}

    fun getPropertiesList() {
        viewModelScope.launch {
            getPropertiesListUseCase.invoke().collect {
                    properties -> propertiesLiveData.postValue(properties)
            }
        }
    }

    fun getAllProperties() {
        viewModelScope.launch {
            getPropertiesListUseCase.invokePropertiesList().collect {
                    properties -> propertiesLD.postValue(properties)
            }
        }
    }
}