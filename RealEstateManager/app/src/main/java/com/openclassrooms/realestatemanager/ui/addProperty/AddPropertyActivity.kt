package com.openclassrooms.realestatemanager.ui.addProperty

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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
    private val amenitiesView : AddPropertyAmenitiesView = AddPropertyAmenitiesView()
    private val addPropertyViewModel : AddPropertyViewModel by viewModels()
    private val picturesList = ArrayList<Image>()
    private lateinit var adapter : AddPropertyRecyclerViewAdapter

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        adapter = AddPropertyRecyclerViewAdapter(picturesList)

        configureListeners()

        lifecycleScope.launch {
            populateAgentsSpinner()
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
        val property : Property = getPropertyToCreate()
        addPropertyViewModel.createProperty(property)
        Toast.makeText(this, "Property create with success !", Toast.LENGTH_SHORT).show()
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
            registerDate = Utils.getTodayDate(),
            pictures = picturesList
        )
    }

    private fun fetchPicturesList(pictures : List<Image>) {
        adapter.updatePicturesList(pictures)
    }

    override fun getImage(image: Image) {
        picturesList.add(image)
        fetchPicturesList(picturesList)
    }

    private suspend fun populateAgentsSpinner() {
        addPropertyViewModel.getAgentsListLD()
        addPropertyViewModel.agentsLiveData.observe(this) {agentsList ->
            fetchAgentsListIntoSpinner(agentsList)
        }
    }

    private fun fetchAgentsListIntoSpinner(agents : List<Agent>) {
        val spinner = binding.addPropertyAgentSpinner
        val adapter : ArrayAdapter<Agent> = ArrayAdapter(this, android.R.layout.simple_spinner_item, agents)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if(spinner != null) {
            spinner.adapter
        }
    }
}