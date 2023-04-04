package com.openclassrooms.realestatemanager.ui.addProperty

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.usecases.CreatePropertyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPropertyFragmentViewModel @Inject constructor(private val createPropertyUseCase : CreatePropertyUseCase) :
    ViewModel() {

    fun createProperty(property : Property) {
        createPropertyUseCase.createProperty(property)
    }


}