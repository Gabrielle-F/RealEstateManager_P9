package com.openclassrooms.realestatemanager.ui.propertyDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.usecases.GetAgentByIdUseCase
import com.openclassrooms.realestatemanager.usecases.GetPropertyByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyDetailsViewModel @Inject constructor(private val getPropertyByIdUseCase: GetPropertyByIdUseCase, private val getAgentByIdUseCase: GetAgentByIdUseCase) : ViewModel() {

    val propertyLiveData = object : MutableLiveData<Property>(){}
    val agentLiveData = object : MutableLiveData<Agent>(){}

    fun getPropertyById(id : Int) {
        viewModelScope.launch {
            getPropertyByIdUseCase.invoke(id).collect {
                property -> propertyLiveData.postValue(property)
            }
        }
    }

    fun getAgentById(id : Int) {
        viewModelScope.launch {
            getAgentByIdUseCase.invoke(id).collect {
                agent -> agentLiveData.postValue(agent)
            }
        }
    }
}