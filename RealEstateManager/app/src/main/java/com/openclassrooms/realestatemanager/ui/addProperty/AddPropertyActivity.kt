package com.openclassrooms.realestatemanager.ui.addProperty

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import com.openclassrooms.realestatemanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPropertyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddPropertyBinding
    private val amenitiesView : AddPropertyAmenitiesView = AddPropertyAmenitiesView()
    private val addPropertyViewModel : AddPropertyViewModel by viewModels()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureListeners()
    }

    fun configureListeners() {
        binding.activityAddFab.setOnClickListener { createProperty() }
        binding.cancelAddFab.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun createProperty() {
        val property : Property = getPropertyToCreate()
        addPropertyViewModel.createProperty(property)
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