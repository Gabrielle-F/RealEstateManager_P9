package com.openclassrooms.realestatemanager.ui.propertyDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyDetailsBinding
import com.openclassrooms.realestatemanager.model.Property
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertyDetailsFragment : Fragment(R.layout.fragment_property_details) {

    private lateinit var binding : FragmentPropertyDetailsBinding
    private val viewModel : PropertyDetailsViewModel by viewModels()

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPropertyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.propertyLiveData.observe(viewLifecycleOwner) {property ->
            fetchPropertyDetails(property)
        }
    }

    @Override
    override fun onStart() {
        super.onStart()
        val bundle = arguments
        var selectedPropertyId = 0
        if (bundle != null) {
            selectedPropertyId = bundle.getInt("selectedPropertyId")
        }
        viewModel.getPropertyById(selectedPropertyId)
    }

    private fun fetchPropertyDetails(property : Property) {
        binding.propertyDetailsCityInfo.setText(property.city)
        binding.propertyDetailsSurfaceInfo.setText(property.area)
        binding.propertyDetailsNumberOfRoomsInfo.setText(property.rooms.toString())

        if(property.school) {
            binding.propertyDetailsPositiveIconSchool.visibility = View.VISIBLE
            binding.propertyDetailsNegativeIconSchool.visibility = View.GONE
        }
        if(property.restaurants) {
            binding.propertyDetailsPositiveIconRestaurants.visibility = View.VISIBLE
            binding.propertyDetailsNegativeIconRestaurants.visibility = View.GONE
        }
        if(property.supermarket) {
            binding.propertyDetailsPositiveIconSupermarket.visibility = View.VISIBLE
            binding.propertyDetailsNegativeIconSupermarket.visibility = View.GONE
        }
        if(property.shoppingArea) {
            binding.propertyDetailsPositiveIconShoppingArea.visibility = View.VISIBLE
            binding.propertyDetailsNegativeIconShoppingArea.visibility = View.GONE
        }
        if(property.playground) {
            binding.propertyDetailsPositiveIconPlayground.visibility = View.VISIBLE
            binding.propertyDetailsNegativeIconPlayground.visibility = View.GONE
        }
        if(property.cinema) {
            binding.propertyDetailsPositiveIconCinema.visibility = View.VISIBLE
            binding.propertyDetailsNegativeIconCinema.visibility = View.GONE
        }
    }
}