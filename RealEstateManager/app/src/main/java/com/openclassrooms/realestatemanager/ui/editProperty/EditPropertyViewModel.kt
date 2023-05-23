package com.openclassrooms.realestatemanager.ui.editProperty

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.usecases.GetAgentsListUseCase
import com.openclassrooms.realestatemanager.usecases.GetPropertyByIdUseCase
import com.openclassrooms.realestatemanager.usecases.UpdatePropertyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPropertyViewModel @Inject constructor(private val getPropertyByIdUseCase: GetPropertyByIdUseCase,
                                                private val getAgentsListUseCase: GetAgentsListUseCase,
                                                private val updatePropertyUseCase: UpdatePropertyUseCase) : ViewModel() {

    val propertyLiveData = object : MutableLiveData<Property>(){}
    val agentsLiveData = object : MutableLiveData<List<Agent>>(){}

    suspend fun updateProperty(property: Property) = updatePropertyUseCase.invoke(property)

    fun getPropertyById(id : Int) {
        viewModelScope.launch {
            getPropertyByIdUseCase.invoke(id).collect {
                    property -> propertyLiveData.postValue(property)
            }
        }
    }

    fun getAgentsListLD() {
        viewModelScope.launch {
            getAgentsListUseCase.invoke().collect { agents ->
                agentsLiveData.value = agents
            }
        }
    }
}