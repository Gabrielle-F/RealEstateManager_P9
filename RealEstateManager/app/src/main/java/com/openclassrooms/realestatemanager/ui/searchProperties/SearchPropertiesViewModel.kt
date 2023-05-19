package com.openclassrooms.realestatemanager.ui.searchProperties

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.usecases.GetAgentsListUseCase
import com.openclassrooms.realestatemanager.usecases.GetSearchPropertiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class SearchPropertiesViewModel @Inject constructor(private val getSearchPropertiesUseCase: GetSearchPropertiesUseCase, private val getAgentsListUseCase: GetAgentsListUseCase) : ViewModel() {

    val searchProperties = object : MutableLiveData<List<Property>>(){}
    val agentsListLiveData = object : MutableLiveData<List<Agent>>(){}

    fun getSearchProperties(minPrice : Int, maxPrice : Int, minArea : Int, maxArea : Int, city: String?,
                            types : List<String>?, rooms : List<Int>?,
                            availability : Boolean?, startDate : String?, endDate : String?, numberOfPictures: List<Int>,
                            agentId : Int?, school : Boolean, restaurants : Boolean, playground : Boolean,
                            supermarket : Boolean, shoppingArea : Boolean, cinema : Boolean) {
        viewModelScope.launch {
            getSearchPropertiesUseCase.invoke(minPrice, maxPrice, minArea, maxArea, city, types, rooms, availability, startDate, endDate, numberOfPictures, agentId, school,
                restaurants, playground, supermarket, shoppingArea, cinema).collect {
                    properties -> searchProperties.postValue(properties)
            }
        }
    }

    suspend fun getAgentsListLD() {
        getAgentsListUseCase.invoke().collect() { agents ->
            agentsListLiveData.postValue(agents)
        }
    }
}