package com.openclassrooms.realestatemanager.ui.addProperty

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import com.openclassrooms.realestatemanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddPropertyActivity : AppCompatActivity(), AddPicturesFragment.OnDataChangeListener {

    private lateinit var binding : ActivityAddPropertyBinding
    private val addPropertyViewModel : AddPropertyViewModel by viewModels()
    private val picturesList = ArrayList<Image>()
    private lateinit var addPropertyAdapter : AddPropertyRecyclerViewAdapter
    private lateinit var selectedAgent : Agent

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        addPropertyAdapter = AddPropertyRecyclerViewAdapter(picturesList)
        val recyclerView = binding.addPropertyPicturesRecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = addPropertyAdapter

        configureListeners()

        lifecycleScope.launch {
            addPropertyViewModel.getAgentsListLD()
        }
        addPropertyViewModel.agentsLiveData.observe(this) {agentsList ->
            Log.d("TAG", "Agents list size: ${agentsList.size}")
            fetchAgentsListIntoSpinner(agentsList)
        }

        if(savedInstanceState != null) {
            binding.addPropertyTypeEdittxt.setText(savedInstanceState.getString("type"))
            binding.addPropertyRoomsEdittxt.setText(savedInstanceState.getInt("rooms").toString())
            binding.addPropertyPriceEdittxt.setText(savedInstanceState.getInt("price").toString())
            binding.addPropertyAreaEdittxt.setText(savedInstanceState.getInt("area").toString())
            binding.addPropertyStreetNumberEdittxt.setText(savedInstanceState.getString("streetNumber"))
            binding.addPropertyStreetEdittxt.setText(savedInstanceState.getString("streetName"))
        }
    }

    fun configureListeners() {
        binding.activityAddFab.setOnClickListener {
            lifecycleScope.launch {
                createProperty()
            }
        }
        binding.cancelAddFab.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
            fragmentManager.beginTransaction().add(android.R.id.content, addPicturesFragment).commit()
        }
    }


    suspend fun createProperty() {
        if(validateFields() && validateFieldsSoldOrAvailable()) {
            val property : Property = getPropertyToCreate()
            addPropertyViewModel.createProperty(property)
            Toast.makeText(this, "Property create with success !", Toast.LENGTH_SHORT).show()
        }
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
            agentId = selectedAgent.id
        )
    }

    private fun fetchPicturesList(pictures : List<Image>) {
        addPropertyAdapter.updatePicturesList(pictures)
    }

    override fun getImage(image: Image) {
        picturesList.add(image)
        fetchPicturesList(picturesList)
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

    private fun validateFields() : Boolean {
        val type = binding.addPropertyTypeEdittxt.text.toString()
        val rooms = binding.addPropertyRoomsEdittxt.text.toString()
        val price = binding.addPropertyPriceEdittxt.text.toString()
        val area = binding.addPropertyAreaEdittxt.text.toString()
        val streetNumber = binding.addPropertyStreetNumberEdittxt.text.toString()
        val streetName = binding.addPropertyStreetEdittxt.text.toString()
        val postalCode = binding.addPropertyPostalCodeEdittxt.text.toString()
        val city = binding.addPropertyCityEdittxt.text.toString()

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
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val rooms = binding.addPropertyRoomsEdittxt.text.toString().toInt()
        val area = binding.addPropertyAreaEdittxt.text.toString().toInt()
        val streetNumber = binding.addPropertyStreetNumberEdittxt.text.toString()
        val streetName = binding.addPropertyStreetEdittxt.text.toString()
        val postalCode = binding.addPropertyPostalCodeEdittxt.text.toString()
        val city = binding.addPropertyCityEdittxt.text.toString()
        val school = schoolCheckBoxIsCheckedOrNot()
        val restaurants = restaurantsCheckBoxIsCheckedOrNot()
        val playground = playgroundCheckBoxIsCheckedOrNot()
        val supermarket = supermarketCheckBoxIsCheckedOrNot()
        val shoppingArea = shoppingAreaCheckBoxIsCheckedOrNot()
        val cinema = cinemaCheckBoxIsCheckedOrNot()

        if(binding.addPropertyTypeEdittxt.text.toString().isNotBlank()) {
            outState.putString("type", binding.addPropertyTypeEdittxt.text.toString())
        }
        if(binding.addPropertyCityEdittxt.text.toString().isNotBlank()) {
            outState.putString("city", binding.addPropertyCityEdittxt.text.toString())
        }
        if(rooms.toString().isNotBlank()) {
            outState.putInt("rooms", rooms)
        }
        if(binding.addPropertyPriceEdittxt.text.toString().isNotBlank()) {
            outState.putString("price", binding.addPropertyPriceEdittxt.text.toString())
        }
        if(binding.addPropertySoldDateEdittxt.text.toString().isNotBlank()) {
            outState.putString("soldDate", binding.addPropertySoldDateEdittxt.text.toString())
        }


        outState.putBoolean("sold", binding.addPropertySwitchSoldOrAvailable.isActivated)
        outState.putStringArrayList("pictures", ArrayList(picturesList.map { it.imageUri }))
    }
}