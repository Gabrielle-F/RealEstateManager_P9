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
class MapViewModel @Inject constructor(private val getPropertiesListUseCase: GetPropertiesListUseCase) :
    ViewModel() {

    val propertiesLD = object : MutableLiveData<List<Property>>() {}

    fun getAllProperties() {
        viewModelScope.launch {
            getPropertiesListUseCase.invoke().collect { properties ->
                propertiesLD.postValue(properties)
            }
        }
    }
}