package com.openclassrooms.realestatemanager.ui.addProperty

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.usecases.CreatePropertyUseCase
import com.openclassrooms.realestatemanager.usecases.GetAgentsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AddPropertyViewModel @Inject constructor(private val createPropertyUseCase : CreatePropertyUseCase, private val getAgentsListUseCase: GetAgentsListUseCase) : ViewModel() {

    val agentsLiveData = object : MutableLiveData<List<Agent>>(){}
    suspend fun createProperty(property : Property) = createPropertyUseCase.createProperty(property)

    suspend fun getAgentsListLD() {
        getAgentsListUseCase.invoke().collect {
            agents -> agentsLiveData.postValue(agents)
        }
    }
}