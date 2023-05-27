package com.openclassrooms.realestatemanager.ui.editProperty

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddEditPropertyBinding
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import com.openclassrooms.realestatemanager.ui.propertyDetails.PropertyDetailsFragment
import com.openclassrooms.realestatemanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditPropertyFragment : Fragment(R.layout.add_edit_property) {

    private lateinit var binding : AddEditPropertyBinding
    private var selectedPropertyId : Int = 0
    private val viewModel : EditPropertyViewModel by viewModels()
    private lateinit var selectedAgent : Agent
    private lateinit var editPropertyAdapter : EditPropertyRecyclerViewAdapter
    private val picturesList = ArrayList<Image>()
    private lateinit var propertyRegisterDate : String

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AddEditPropertyBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editPropertyAdapter = EditPropertyRecyclerViewAdapter()
        binding.addPropertyPicturesRecyclerView.adapter = editPropertyAdapter

        viewModel.propertyLiveData.observe(viewLifecycleOwner) { property ->
            fetchPropertyData(property)
        }

        viewModel.agentsLiveData.observe(viewLifecycleOwner) { agents ->
            fetchAgentsListIntoSpinner(agents)
        }

        configureListeners()
    }

    @Override
    override fun onStart() {
        super.onStart()
        val bundle = arguments
        if(bundle != null) {
            selectedPropertyId = bundle.getInt("selectedPropertyId")
        }
        viewModel.getPropertyById(selectedPropertyId)
        viewModel.getAgentsListLD()
    }

    private fun configureListeners() {
        binding.activityAddFab.setOnClickListener {
            lifecycleScope.launch {
                updateProperty()
            }
        }
        binding.cancelAddFab.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    suspend fun updateProperty() {
        val property : Property = getUpdateProperty()
        viewModel.updateProperty(property)
        Toast.makeText(requireContext(), "Property update with success !", Toast.LENGTH_SHORT).show()
        fragmentManager?.popBackStackImmediate()
    }

    private fun getUpdateProperty(): Property {
        return Property(
            id = selectedPropertyId,
            type = binding.addPropertyTypeEdittxt.text.toString(),
            rooms = binding.addPropertyRoomsEdittxt.text.toString().toInt(),
            price = binding.addPropertyPriceEdittxt.text.toString().toInt(),
            area = binding.addPropertyAreaEdittxt.text.toString().toInt(),
            streetNumber = binding.addPropertyStreetNumberEdittxt.text.toString(),
            streetName = binding.addPropertyStreetEdittxt.text.toString(),
            postalCode = binding.addPropertyPostalCodeEdittxt.text.toString(),
            city = binding.addPropertyCityEdittxt.text.toString(),
            school = schoolCheckBoxIsCheckedOrNot(),
            restaurants = restaurantsCheckBoxIsCheckedOrNot(),
            playground = playgroundCheckBoxIsCheckedOrNot(),
            shoppingArea = shoppingAreaCheckBoxIsCheckedOrNot(),
            supermarket = supermarketCheckBoxIsCheckedOrNot(),
            cinema = cinemaCheckBoxIsCheckedOrNot(),
            sold = binding.addPropertySwitchSoldOrAvailable.isChecked,
            soldDate = binding.addPropertySoldDateEdittxt.text.toString(),
            registerDate = propertyRegisterDate,
            pictures = picturesList,
            numberOfPictures = picturesList.size,
            description = binding.addPropertyDescriptionEditTxt.text.toString(),
            latLng = convertAddressToLatLng(),
            agentId = selectedAgent.id
        )
    }

    private fun fetchPropertyData(property: Property) {
        binding.addPropertyTypeEdittxt.setText(property.type)
        binding.addPropertyRoomsEdittxt.setText(property.rooms.toString())
        binding.addPropertyCityEdittxt.setText(property.city)
        binding.addPropertyPriceEdittxt.setText(property.price.toString())
        binding.addPropertyAreaEdittxt.setText(property.area.toString())
        binding.addPropertyDescriptionEditTxt.setText(property.description)
        binding.addPropertyStreetEdittxt.setText(property.streetName)
        binding.addPropertyStreetNumberEdittxt.setText(property.streetNumber)
        binding.addPropertyPostalCodeEdittxt.setText(property.postalCode)
        editPropertyAdapter.updatePicturesList(property.pictures)

        binding.schoolCheckbox.isChecked = property.school
        binding.restaurantsCheckbox.isChecked = property.restaurants
        binding.playgroundCheckbox.isChecked = property.playground
        binding.shoppingAreaCheckbox.isChecked = property.shoppingArea
        binding.supermarketCheckbox.isChecked = property.supermarket
        binding.cinemaCheckbox.isChecked = property.cinema

        binding.addPropertySwitchSoldOrAvailable.isChecked = property.sold
        binding.addPropertySoldDateEdittxt.setText(property.soldDate)
        propertyRegisterDate = property.registerDate

        var propertyLatLng : LatLng? = null
        val agentId = property.agentId
        binding.addPropertyAgentSpinner.id = agentId
        if(property.latLng != null) {
            propertyLatLng = property.latLng
        }
    }

    fun convertAddressToLatLng() : LatLng? {
        val streetNumber = binding.addPropertyStreetNumberEdittxt.text.toString()
        val streetName = binding.addPropertyStreetEdittxt.text.toString()
        val postalCode = binding.addPropertyPostalCodeEdittxt.text.toString()
        val city = binding.addPropertyCityEdittxt.text.toString()
        val geocoder = Geocoder(requireActivity())
        var latLng: LatLng?
        val addressParts = listOf(streetNumber, streetName, postalCode, city)
        val address = addressParts.joinToString(", ")
        val addressList = geocoder.getFromLocationName(address, 1)
        if(addressList != null && addressList.isNotEmpty()) {
            val location = addressList[0]
            val latitude = location.latitude
            val longitude = location.longitude
            latLng = LatLng(latitude, longitude)
        } else {
            latLng = null
        }
        return latLng
    }

    private fun fetchAgentsListIntoSpinner(agents: List<Agent>) {
        val spinner = binding.addPropertyAgentSpinner
        val adapter : ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, agents.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if(spinner != null) {
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedAgent = agents[position]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
    }

    private fun schoolCheckBoxIsCheckedOrNot() : Boolean {
        val school : Boolean
        val schoolCheckbox : CheckBox = binding.schoolCheckbox
        school = schoolCheckbox.isChecked
        return school
    }

    private fun restaurantsCheckBoxIsCheckedOrNot() : Boolean {
        val restaurants : Boolean
        val restaurantsCheckbox : CheckBox = binding.restaurantsCheckbox
        restaurants = restaurantsCheckbox.isChecked
        return restaurants
    }

    private fun playgroundCheckBoxIsCheckedOrNot() : Boolean {
        val playground : Boolean
        val playgroundCheckbox : CheckBox = binding.playgroundCheckbox
        playground = playgroundCheckbox.isChecked
        return playground
    }

    private fun supermarketCheckBoxIsCheckedOrNot() : Boolean {
        val supermarket : Boolean
        val supermarketCheckbox : CheckBox = binding.supermarketCheckbox
        supermarket = supermarketCheckbox.isChecked
        return supermarket
    }

    private fun shoppingAreaCheckBoxIsCheckedOrNot() : Boolean {
        val shoppingArea : Boolean
        val shoppingAreaCheckbox : CheckBox = binding.shoppingAreaCheckbox
        shoppingArea = shoppingAreaCheckbox.isChecked
        return shoppingArea
    }

    private fun cinemaCheckBoxIsCheckedOrNot() : Boolean {
        val cinema : Boolean
        val cinemaCheckbox : CheckBox = binding.cinemaCheckbox
        cinema = cinemaCheckbox.isChecked
        return cinema
    }
}