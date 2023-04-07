package com.openclassrooms.realestatemanager.ui.propertiesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.usecases.GetPropertiesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertiesListViewModel @Inject constructor(private val getAllPropertiesListUseCase: GetPropertiesListUseCase) : ViewModel() {

    val properties : LiveData<List<Property>> = getAllPropertiesListUseCase.properties.asLiveData()
}