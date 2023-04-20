package com.openclassrooms.realestatemanager.ui.addProperty

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import com.openclassrooms.realestatemanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPropertyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddPropertyBinding
    private val amenitiesView : AddPropertyAmenitiesView = AddPropertyAmenitiesView()
    private val addPropertyViewModel : AddPropertyViewModel by viewModels()
    private val RETRIEVE_SELECTED_PICTURE_REQUEST_CODE : Int = 10
    private lateinit var picturesMutableList: MutableList<Image>

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
        binding.addPropertyAddPicturesMaterialBtn.setOnClickListener {
            val getSelectedPictureIntent = Intent(this, AddPicturesFragment::class.java)
            startActivityForResult(getSelectedPictureIntent, RETRIEVE_SELECTED_PICTURE_REQUEST_CODE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RETRIEVE_SELECTED_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                val imagePath = data.getStringExtra("ImagePath")!!
                val imageName = data.getStringExtra("ImageName")
                val imageDescription = data.getStringExtra("ImageDescription")
                val imageDefineAsFirst = data.getBooleanExtra("DefineAsFirstImage", true)

                val imageToAdd = Image(imagePath, imageName, imageDescription, imageDefineAsFirst)

                picturesMutableList.add(imageToAdd)
            }

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
            registerDate = Utils.getTodayDate(),
            pictures = picturesMutableList
        )
    }
}