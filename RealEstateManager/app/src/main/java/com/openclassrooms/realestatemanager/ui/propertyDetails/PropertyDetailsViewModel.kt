package com.openclassrooms.realestatemanager.ui.propertyDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.usecases.GetPropertyByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyDetailsViewModel @Inject constructor(
    private val getPropertyByIdUseCase: GetPropertyByIdUseCase
) : ViewModel() {

    val propertyLiveData = object : MutableLiveData<Property>() {}

    fun getPropertyById(id: String) {
        viewModelScope.launch {
            getPropertyByIdUseCase.invoke(id)?.collect { property ->
                propertyLiveData.postValue(property)
            }
        }
    }
}