package com.openclassrooms.realestatemanager.ui.addProperty

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyFirestore
import com.openclassrooms.realestatemanager.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPropertyViewModel @Inject constructor(private val createPropertyUseCase : CreatePropertyUseCase,
                                                   private val getAgentsListUseCase: GetAgentsListUseCase,
                                                   private val getPropertyByIdUseCase: GetPropertyByIdUseCase,
                                                   private val updatePropertyUseCase: UpdatePropertyUseCase,
                                                   private val createPropertyInFirestoreUseCase: CreatePropertyInFirestoreUseCase) : ViewModel() {

    val agentsLiveData = object : MutableLiveData<List<Agent>>(){}
    val propertyLiveData = object : MutableLiveData<Property>(){}

    suspend fun createProperty(property : Property) = createPropertyUseCase.createProperty(property)

    fun createPropertyInFirestore(property: PropertyFirestore) {
        viewModelScope.launch {
            createPropertyInFirestoreUseCase.invoke(property)
        }
    }

    suspend fun updateProperty(property : Property) = updatePropertyUseCase.invoke(property)

    suspend fun getAgentsListLD() {
        getAgentsListUseCase.invoke().collect { agents ->
            agentsLiveData.value = agents
        }
    }

    fun getPropertyById(id : Int) {
        viewModelScope.launch {
            getPropertyByIdUseCase.invoke(id)?.collect { property -> propertyLiveData.postValue(property)
            }
        }
    }
}