package com.openclassrooms.realestatemanager.ui.searchProperties

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.usecases.GetAgentsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchPropertiesViewModel @Inject constructor(private val getAgentsListUseCase: GetAgentsListUseCase) : ViewModel() {

    val searchProperties = object : MutableLiveData<List<Property>>(){}
    val agentsListLiveData = object : MutableLiveData<List<Agent>>(){}

    suspend fun getAgentsListLD() {
        getAgentsListUseCase.invoke().collect { agents ->
            agentsListLiveData.postValue(agents)
        }
    }
}