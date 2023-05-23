package com.openclassrooms.realestatemanager.ui.propertyDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyDetailsBinding
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.editProperty.EditPropertyFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertyDetailsFragment : Fragment(R.layout.fragment_property_details) {

    private lateinit var binding : FragmentPropertyDetailsBinding
    private val viewModel : PropertyDetailsViewModel by viewModels()
    private lateinit var picturesAdapter : PropertyDetailsRecyclerViewAdapter
    private var selectedPropertyId : Int = 0

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPropertyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        picturesAdapter = PropertyDetailsRecyclerViewAdapter()
        binding.propertyDetailsRecyclerView.adapter = picturesAdapter

        viewModel.propertyLiveData.observe(viewLifecycleOwner) {property ->
            fetchPropertyDetails(property)
        }
        viewModel.agentLiveData.observe(viewLifecycleOwner) { agent ->
            fetchAgent(agent)
        }

        binding.propertyDetailsEditPropertyMaterialBtn.setOnClickListener {
            val editPropertyFragment = EditPropertyFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val bundle = Bundle()
            bundle.putInt("selectedPropertyId", selectedPropertyId)
            editPropertyFragment.arguments = bundle
            fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, editPropertyFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    @Override
    override fun onStart() {
        super.onStart()
        val bundle = arguments
        if (bundle != null) {
            selectedPropertyId = bundle.getInt("selectedPropertyId")
        }
        viewModel.getPropertyById(selectedPropertyId)
    }

    private fun fetchPropertyDetails(property : Property) {
        binding.propertyDetailsTypeInfo.setText(property.type)
        binding.propertyDetailsCityInfo.setText(property.city)
        binding.propertyDetailsSurfaceInfo.setText(property.area.toString())
        binding.propertyDetailsNumberOfRoomsInfo.setText(property.rooms.toString())
        val address = getString(R.string.details_address_with_number, property.streetNumber, property.streetName, property.postalCode)
        binding.propertyDetailsLocationAddress.setText(address)
        binding.propertyDetailsPriceInfo.setText(property.price.toString())
        binding.propertyDetailsRegisterDateInfo.setText(property.registerDate)
        binding.propertyDetailsDescriptionContent.setText(property.description)
        picturesAdapter.updatePicturesList(property.pictures)
        if(property.sold) {
            binding.propertyDetailsSoldDateInfo.setText(property.soldDate)
        } else {
            binding.propertyDetailsSoldDateInfo.setText("?")
        }
        val agentId = property.agentId
        viewModel.getAgentById(agentId)

        if(property.sold) {
            binding.propertyDetailsAvailableInfo.visibility = View.INVISIBLE
            binding.propertyDetailsSoldInfo.visibility = View.VISIBLE
        }
        if(property.school) {
            binding.propertyDetailsNegativeIconSchool.visibility = View.INVISIBLE
        } else {
            binding.propertyDetailsPositiveIconSchool.visibility = View.INVISIBLE
        }
        if(property.restaurants) {
            binding.propertyDetailsNegativeIconRestaurants.visibility = View.INVISIBLE
        } else {
            binding.propertyDetailsPositiveIconRestaurants.visibility = View.INVISIBLE
        }
        if(property.supermarket) {
            binding.propertyDetailsNegativeIconSupermarket.visibility = View.INVISIBLE
        } else {
            binding.propertyDetailsPositiveIconSupermarket.visibility = View.INVISIBLE
        }
        if(property.shoppingArea) {
            binding.propertyDetailsNegativeIconShoppingArea.visibility = View.INVISIBLE
        } else {
            binding.propertyDetailsPositiveIconShoppingArea.visibility = View.INVISIBLE
        }
        if(property.playground) {
            binding.propertyDetailsNegativeIconPlayground.visibility = View.INVISIBLE
        } else {
            binding.propertyDetailsPositiveIconPlayground.visibility = View.INVISIBLE
        }
        if(property.cinema) {
            binding.propertyDetailsNegativeIconCinema.visibility = View.INVISIBLE
        } else {
            binding.propertyDetailsPositiveIconCinema.visibility = View.INVISIBLE
        }
    }

    private fun fetchAgent(agent : Agent) {
        binding.propertyDetailsAgentInfo.setText(agent.name)
    }
}