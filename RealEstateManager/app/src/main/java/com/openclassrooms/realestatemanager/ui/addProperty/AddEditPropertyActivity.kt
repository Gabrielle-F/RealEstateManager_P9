package com.openclassrooms.realestatemanager.ui.addProperty

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.databinding.ActivityAddEditPropertyBinding
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyFirestore
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import com.openclassrooms.realestatemanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditPropertyActivity : AppCompatActivity(), AddPicturesFragment.OnDataChangeListener {

    private lateinit var binding : ActivityAddEditPropertyBinding
    private val viewModel : AddEditPropertyViewModel by viewModels()
    private val picturesList = ArrayList<Image>()
    private val picturesUriList = ArrayList<String>()
    private lateinit var addPropertyAdapter : AddPropertyRecyclerViewAdapter
    private lateinit var selectedAgent : Agent
    private lateinit var propertyRegisterDate : String
    private var selectedPropertyId : String = "propertyId"

    interface OnPropertyAddedOrUpdatedListener {
        fun onPropertyAddedOrUpdated()
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditPropertyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        addPropertyAdapter = AddPropertyRecyclerViewAdapter()
        val recyclerView = binding.addPropertyPicturesRecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = addPropertyAdapter

        configureListeners()


        lifecycleScope.launch {
            viewModel.getAgentsListLD()
        }
        viewModel.propertyLiveData.observe(this) { property ->
            fetchPropertyData(property)
        }
        viewModel.agentsLiveData.observe(this) { agentsList ->
            Log.d("TAG", "Agents list size: ${agentsList.size}")
            fetchAgentsListIntoSpinner(agentsList)
        }
    }

    @Override
    override fun onStart() {
        super.onStart()
        selectedPropertyId = intent.getStringExtra("selectedPropertyId").toString()
        viewModel.getPropertyById(selectedPropertyId)
    }

    fun configureListeners() {
        val addPropertyAddedOrUpdatedListener = object : OnPropertyAddedOrUpdatedListener{
            override fun onPropertyAddedOrUpdated() {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }
        binding.activityAddFab.setOnClickListener {
            createProperty()
            addPropertyAddedOrUpdatedListener.onPropertyAddedOrUpdated()
        }
        binding.cancelAddFab.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.updateFab.setOnClickListener {
            lifecycleScope.launch {
                updateProperty()
            }
            addPropertyAddedOrUpdatedListener.onPropertyAddedOrUpdated()
        }
        binding.addPropertyAddPicturesMaterialBtn.setOnClickListener {
            val addPicturesFragment = AddPicturesFragment()
            val fragmentManager = supportFragmentManager
            var hasFirstPicture = false
            for (image in picturesList) {
                if (image.firstPicture == true) {
                    hasFirstPicture == true
                    break
                }
            }
            val bundle = Bundle()
            bundle.putBoolean("hasFirstPicture", hasFirstPicture)
            addPicturesFragment.arguments = bundle
            addPicturesFragment.show(fragmentManager, "Show AddPicturesFragment")
        }
    }


    fun createProperty() {
        if(validateFields() && validateFieldsSoldOrAvailable()) {
            val propertyFirestore : PropertyFirestore = getPropertyFirestore()
            val propertyCreatedIdString = viewModel.createPropertyInFirestoreDb(propertyFirestore)
            val property : Property = getPropertyToCreate(propertyCreatedIdString)
            viewModel.createPropertyInLocalDb(property)
            Toast.makeText(this, "Property create with success !", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun updateProperty() {
        val property : Property = getUpdateProperty()
        viewModel.updateProperty(property)
        Toast.makeText(this, "Property update with success !", Toast.LENGTH_SHORT).show()
    }

    fun getPropertyToCreate(id : String) : Property {
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
            school = schoolCheckBoxIsCheckedOrNot(),
            restaurants = restaurantsCheckBoxIsCheckedOrNot(),
            playground = playgroundCheckBoxIsCheckedOrNot(),
            supermarket = supermarketCheckBoxIsCheckedOrNot(),
            shoppingArea = shoppingAreaCheckBoxIsCheckedOrNot(),
            cinema = cinemaCheckBoxIsCheckedOrNot(),
            sold = binding.addPropertySwitchSoldOrAvailable.isActivated,
            soldDate = binding.addPropertySoldDateEdittxt.text.toString(),
            registerDate = Utils.getTodayDate(),
            pictures = picturesList,
            numberOfPictures = picturesList.size,
            description = binding.addPropertyDescriptionEditTxt.text.toString(),
            latitude = convertAddressToLatLng().latitude,
            longitude = convertAddressToLatLng().longitude,
            agentName = selectedAgent.name
        )
    }

    private fun getPropertyFirestore() : PropertyFirestore {
        return PropertyFirestore(
            type = binding.addPropertyTypeEdittxt.text.toString(),
            price = binding.addPropertyPriceEdittxt.text.toString().toInt(),
            streetName = binding.addPropertyStreetEdittxt.text.toString(),
            streetNumber = binding.addPropertyStreetNumberEdittxt.text.toString(),
            area = binding.addPropertyAreaEdittxt.text.toString().toInt(),
            rooms = binding.addPropertyRoomsEdittxt.text.toString().toInt(),
            postalCode = binding.addPropertyPostalCodeEdittxt.text.toString(),
            city = binding.addPropertyCityEdittxt.text.toString(),
            sold = binding.addPropertySwitchSoldOrAvailable.isActivated,
            registerDate = Utils.getTodayDate(),
            soldDate = binding.addPropertySoldDateEdittxt.text.toString(),
            school = schoolCheckBoxIsCheckedOrNot(),
            restaurants = restaurantsCheckBoxIsCheckedOrNot(),
            playground = playgroundCheckBoxIsCheckedOrNot(),
            supermarket = supermarketCheckBoxIsCheckedOrNot(),
            shoppingArea = shoppingAreaCheckBoxIsCheckedOrNot(),
            cinema = cinemaCheckBoxIsCheckedOrNot(),
            picturesUri = picturesUriList,
            numberOfPictures = picturesUriList.size,
            description = binding.addPropertyDescriptionEditTxt.text.toString(),
            latLng = convertAddressToLatLng(),
            agentId = selectedAgent.id,
        )
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
            latitude = convertAddressToLatLng().latitude,
            longitude = convertAddressToLatLng().longitude,
            agentName = selectedAgent.name
        )
    }

    private fun fetchPropertyData(property: Property?) {
        if(property != null) {
            binding.addPropertyTypeEdittxt.setText(property.type)
            binding.addPropertyRoomsEdittxt.setText(property.rooms.toString())
            binding.addPropertyCityEdittxt.setText(property.city)
            binding.addPropertyPriceEdittxt.setText(property.price.toString())
            binding.addPropertyAreaEdittxt.setText(property.area.toString())
            binding.addPropertyDescriptionEditTxt.setText(property.description)
            binding.addPropertyStreetEdittxt.setText(property.streetName)
            binding.addPropertyStreetNumberEdittxt.setText(property.streetNumber)
            binding.addPropertyPostalCodeEdittxt.setText(property.postalCode)
            addPropertyAdapter.updatePicturesList(property.pictures)

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
            /**val agentId = property.agentName
            binding.addPropertyAgentSpinner.id = agentId*/
            val latitude = property.latitude
            val longitude = property.longitude
            val latLng = LatLng(latitude, longitude)
            if(latLng != null) {
                propertyLatLng = latLng
            }
        }
    }

    fun convertAddressToLatLng() : LatLng {
        val streetNumber = binding.addPropertyStreetNumberEdittxt.text.toString()
        val streetName = binding.addPropertyStreetEdittxt.text.toString()
        val postalCode = binding.addPropertyPostalCodeEdittxt.text.toString()
        val city = binding.addPropertyCityEdittxt.text.toString()
        val geocoder = Geocoder(this)
        var latLng : LatLng
        val addressParts = listOf(streetNumber, streetName, postalCode, city)
        val address = addressParts.joinToString(", ")
        val addressList = geocoder.getFromLocationName(address, 1)
        if(addressList != null && addressList.isNotEmpty()) {
            val location = addressList[0]
            val latitude = location.latitude
            val longitude = location.longitude
            latLng = LatLng(latitude, longitude)
        } else {
            latLng = LatLng(0.0, 0.0)
        }
        return latLng
    }

    override fun getImage(image: Image) {
        val pictureUri = image.imageUri
        picturesList.add(image)
        addPropertyAdapter.updatePicturesList(picturesList)
        picturesUriList.add(pictureUri)
    }

    private fun fetchAgentsListIntoSpinner(agents : List<Agent>) {
        val spinner = binding.addPropertyAgentSpinner
        val adapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, agents.map { it.name })
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

    private fun validateFields() : Boolean {
        val type = binding.addPropertyTypeEdittxt.text.toString()
        val rooms = binding.addPropertyRoomsEdittxt.text.toString()
        val price = binding.addPropertyPriceEdittxt.text.toString()
        val area = binding.addPropertyAreaEdittxt.text.toString()
        val streetNumber = binding.addPropertyStreetNumberEdittxt.text.toString()
        val streetName = binding.addPropertyStreetEdittxt.text.toString()
        val postalCode = binding.addPropertyPostalCodeEdittxt.text.toString()
        val city = binding.addPropertyCityEdittxt.text.toString()
        val description = binding.addPropertyDescriptionEditTxt.text.toString()

        return when {
            type.isEmpty() -> {
                binding.addPropertyTypeEdittxt.error = "Type cannot be empty"
                false
            }
            rooms.isEmpty() -> {
                binding.addPropertyRoomsEdittxt.error = "Rooms cannot be empty"
                false
            }
            price.isEmpty() -> {
                binding.addPropertyPriceEdittxt.error = "Price cannot be empty"
                false
            }
            area.isEmpty() -> {
                binding.addPropertyAreaEdittxt.error = "Area cannot be empty"
                false
            }
            streetNumber.isEmpty() -> {
                binding.addPropertyStreetNumberEdittxt.error = "Street number cannot be empty"
                false
            }
            streetName.isEmpty() -> {
                binding.addPropertyStreetEdittxt.error = "Street name cannot be empty"
                false
            }
            postalCode.isEmpty() -> {
                binding.addPropertyPostalCodeEdittxt.error = "Postal code cannot be empty"
                false
            }
            city.isEmpty() -> {
                binding.addPropertyCityEdittxt.error = "City cannot be empty"
                false
            }
            description.isEmpty() -> {
                binding.addPropertyDescriptionEditTxt.error = "Description cannot be empty"
                false
            }
            else -> true
        }
    }

    private fun validateFieldsSoldOrAvailable() : Boolean {
        val sold = binding.addPropertySwitchSoldOrAvailable.isActivated
        val soldDate = binding.addPropertySoldDateEdittxt.text.toString()

        if(sold){
            if (soldDate.isEmpty()) {
                binding.addPropertySoldDateEdittxt.error = "Sold date cannot be empty"
                return false
            }
        }
        return true
    }
}