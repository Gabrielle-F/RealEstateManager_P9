package com.openclassrooms.realestatemanager.ui.propertiesList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.usecases.GetPropertiesListUseCase
import com.openclassrooms.realestatemanager.usecases.GetSearchPropertiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertiesListViewModel @Inject constructor(
    private val getAllPropertiesListUseCase: GetPropertiesListUseCase,
    private val getSearchPropertiesUseCase: GetSearchPropertiesUseCase
) : ViewModel() {

    val propertiesLD = object : MutableLiveData<List<Property>>() {}
    val searchProperties = object : MutableLiveData<List<Property>>() {}
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun getAllProperties() {
        viewModelScope.launch {
            getAllPropertiesListUseCase.invoke().collect { properties ->
                propertiesLD.postValue(properties)
            }
        }
    }

    fun getFilteredList(
        minPrice: Int, maxPrice: Int, minArea: Int, maxArea: Int, city: String?,
        types: List<String>?, rooms: List<Int>?,
        availability: Boolean?, startDate: String?, endDate: String?, numberOfPictures: List<Int>?,
        agentName: String?, school: Boolean, restaurants: Boolean, playground: Boolean,
        supermarket: Boolean, shoppingArea: Boolean, cinema: Boolean
    ) {
        coroutineScope.launch {
            getSearchPropertiesUseCase.invoke(
                minPrice,
                maxPrice,
                minArea,
                maxArea,
                city,
                types,
                rooms,
                availability,
                startDate,
                endDate,
                numberOfPictures,
                agentName,
                school,
                restaurants,
                playground,
                supermarket,
                shoppingArea,
                cinema
            ).collect { properties ->
                searchProperties.postValue(properties)
            }
        }
    }
}