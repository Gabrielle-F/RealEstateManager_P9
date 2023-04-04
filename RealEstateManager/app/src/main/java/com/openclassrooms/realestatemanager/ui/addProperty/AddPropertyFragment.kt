package com.openclassrooms.realestatemanager.ui.addProperty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentAddPropertyBinding
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPropertyFragment : Fragment(R.layout.fragment_add_property) {

    private lateinit var binding : FragmentAddPropertyBinding
    private val amenitiesView : AddPropertyAmenitiesView = AddPropertyAmenitiesView()
    private val addPropertyFragmentViewModel : AddPropertyFragmentViewModel by viewModels()

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureListeners()
    }

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddPropertyBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    fun configureListeners() {
        binding.activityAddFab.setOnClickListener { createProperty() }
    }

    fun createProperty() {
        val property : Property = getPropertyToCreate()
        addPropertyFragmentViewModel.createProperty(property)
    }

    fun getPropertyToCreate(id : Int = 0) : Property {
        return Property(
            id = id,
            type = binding.addPropertyTypeEdittxt.text.toString(),
            rooms = binding.addPropertyRoomsEdittxt.text.toString().toInt(),
            price = binding.addPropertyPriceEdittxt.text.toString().toInt(),
            area = binding.addPropertyAreaEdittxt.text.toString().toInt(),
            streetNumber = binding.addPropertyStreetNumberEdittxt.text.toString(),
            streetName = binding.addPropertyStreetEdittxt.text.toString(),
            postalCode = binding.addPropertyPostalCodeEdittxt.text.toString(),
            city = binding.addPropertyCityEdittxt.text.toString(),
            school = amenitiesView.schoolCheckBoxIsCheckedOrNot(),
            restaurants = amenitiesView.restaurantsCheckBoxIsCheckedOrNot(),
            playground = amenitiesView.playgroundCheckBoxIsCheckedOrNot(),
            supermarket = amenitiesView.supermarketCheckBoxIsCheckedOrNot(),
            shoppingArea = amenitiesView.shoppingAreaCheckBoxIsCheckedOrNot(),
            cinema = amenitiesView.cinemaCheckBoxIsCheckedOrNot(),
            sold = binding.addPropertySwitchSoldOrAvailable.isActivated,
            soldDate = binding.addPropertySoldDateEdittxt.text.toString(),
            registerDate = Utils.getTodayDate()
        )
    }
}