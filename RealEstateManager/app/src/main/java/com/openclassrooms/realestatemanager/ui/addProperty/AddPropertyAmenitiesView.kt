package com.openclassrooms.realestatemanager.ui.addProperty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AmenitiesCheckboxBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPropertyAmenitiesView : Fragment(R.layout.amenities_checkbox) {

    private lateinit var binding : AmenitiesCheckboxBinding

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AmenitiesCheckboxBinding.bind(view)
    }

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.amenities_checkbox, container, false)
    }

    fun schoolCheckBoxIsCheckedOrNot() : Boolean {
        val school : Boolean
        val schoolCheckbox : CheckBox = binding.schoolCheckbox
        school = schoolCheckbox.isChecked
        return school
    }

    fun restaurantsCheckBoxIsCheckedOrNot() : Boolean {
        val restaurants : Boolean
        val restaurantsCheckbox : CheckBox = binding.restaurantsCheckbox
        restaurants = restaurantsCheckbox.isChecked
        return restaurants
    }

    fun playgroundCheckBoxIsCheckedOrNot() : Boolean {
        val playground : Boolean
        val playgroundCheckbox : CheckBox = binding.playgroundCheckbox
        playground = playgroundCheckbox.isChecked
        return playground
    }

    fun supermarketCheckBoxIsCheckedOrNot() : Boolean {
        val supermarket : Boolean
        val supermarketCheckbox : CheckBox = binding.supermarketCheckbox
        supermarket = supermarketCheckbox.isChecked
        return supermarket
    }

    fun shoppingAreaCheckBoxIsCheckedOrNot() : Boolean {
        val shoppingArea : Boolean
        val shoppingAreaCheckbox : CheckBox = binding.shoppingAreaCheckbox
        shoppingArea = shoppingAreaCheckbox.isChecked
        return shoppingArea
    }

    fun cinemaCheckBoxIsCheckedOrNot() : Boolean {
        val cinema : Boolean
        val cinemaCheckbox : CheckBox = binding.cinemaCheckbox
        cinema = cinemaCheckbox.isChecked
        return cinema
    }
}