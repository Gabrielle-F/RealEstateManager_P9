package com.openclassrooms.realestatemanager.ui.searchProperties

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.slider.RangeSlider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchPropertiesBinding
import com.openclassrooms.realestatemanager.model.Agent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SearchPropertiesFragment : Fragment(R.layout.fragment_search_properties) {

    private lateinit var context : Context
    private lateinit var binding : FragmentSearchPropertiesBinding
    private val searchPropertiesViewModel : SearchPropertiesViewModel by viewModels()
    private var minPrice : Int = 0
    private var maxPrice : Int = 0
    private var minArea : Int = 0
    private var maxArea : Int = 0
    private var schoolAmenitie : Boolean = false
    private var restaurantsAmenitie : Boolean = false
    private var playgroundAmenitie : Boolean = false
    private var shoppingAreaAmenitie : Boolean = false
    private var supermarketAmenitie : Boolean = false
    private var cinemaAmenitie : Boolean = false
    private var selectedAgent : Agent? = null
    private var onParametersSelectedListener : OnParametersSelected? = null


    interface OnParametersSelected {
        fun filterList(minPrice: Int, maxPrice: Int, minArea: Int, maxArea: Int, city: String?,
                       types: List<String>?, rooms: List<Int>?, availability: Boolean?, startDate: String?,
                       endDate: String?, numberOfPictures: List<Int>?, agentName: String?, school: Boolean, restaurants: Boolean,
                       playground: Boolean, supermarket: Boolean, shoppingArea: Boolean, cinema: Boolean)
    }
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchPropertiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context = requireContext()
        binding.searchScrollView.isNestedScrollingEnabled = true

        searchPropertiesViewModel.agentsListLiveData.observe(requireActivity()) { agentsList ->
            fetchAgentsListIntoSpinner(agentsList)
        }

        configureListener()
    }

    @Override
    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            searchPropertiesViewModel.getAgentsListLD()
        }
    }

    private fun configureListener() {
        val filterListListener = object : OnParametersSelected{
            override fun filterList(
                minPrice: Int, maxPrice: Int, minArea: Int, maxArea: Int, city: String?, types: List<String>?,
                rooms: List<Int>?, availability: Boolean?, startDate: String?, endDate: String?,
                numberOfPictures: List<Int>?, agentName: String?, school: Boolean, restaurants: Boolean,
                playground: Boolean, supermarket: Boolean, shoppingArea: Boolean, cinema: Boolean
            ) {
                requireActivity().supportFragmentManager.beginTransaction().remove(this@SearchPropertiesFragment).commit()
            }
        }

        binding.searchStartDateEditTxt.setOnClickListener {
            showDatePickerDialog(binding.searchStartDateEditTxt)
        }
        binding.searchEndDateEditTxt.setOnClickListener {
            showDatePickerDialog(binding.searchEndDateEditTxt)
        }

        binding.searchMaterialBtn.setOnClickListener {
            getSelectedAmenities()
            val city : String = binding.searchCityEditTxt.text.toString()
            val startDate : String = binding.searchStartDateEditTxt.text.toString()
            val endDate : String = binding.searchEndDateEditTxt.text.toString()
            minPrice = binding.searchRangeSliderPrice.values[0].toInt()
            maxPrice = binding.searchRangeSliderPrice.values[1].toInt()
            minArea = binding.searchRangeSliderArea.values[0].toInt()
            maxArea = binding.searchRangeSliderArea.values[1].toInt()
            onParametersSelectedListener?.filterList(minPrice, maxPrice, minArea, maxArea, city, getSelectedTypesList(),
                getSelectedNumberOfRooms(), getAvailabilityChoice(), startDate, endDate, getSelectedNumberOfPictures(),
                selectedAgent?.name, schoolAmenitie, restaurantsAmenitie, playgroundAmenitie,
                supermarketAmenitie, shoppingAreaAmenitie, cinemaAmenitie)
        }
    }

    fun setOnParametersSelectedListener(listener: OnParametersSelected) {
        onParametersSelectedListener = listener
    }

    private fun getSelectedTypesList() : List<String>? {
        val selectedTypes = mutableListOf<String>()
        val chipGroup = binding.searchTypeChipGroup

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            if(chip.isChecked) {
                selectedTypes.add(chip.text.toString())
            }
        }
        return selectedTypes
    }

    private fun getSelectedNumberOfRoomsStringList() : List<String>? {
        val selectedNumbersOfRooms = mutableListOf<String>()
        val chipGroup = binding.searchRoomsChipGroup

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            if(chip.isChecked) {
                selectedNumbersOfRooms.add(chip.text.toString())
            }
        }
        return selectedNumbersOfRooms
    }

    private fun getSelectedNumberOfRooms(): List<Int>? {
        val selectedStringRooms: List<String>? = getSelectedNumberOfRoomsStringList()
        val selectedIntRooms = mutableListOf<Int>()

        selectedStringRooms?.let { rooms ->
            val roomValues = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")

            for (roomValue in roomValues) {
                if (rooms.contains(roomValue)) {
                    selectedIntRooms.add(roomValue.toInt())
                }
            }
        }
        return selectedIntRooms
    }

    private fun getSelectedStringsProximity(): List<String> {
        val selectedProximityList = mutableListOf<String>()
        val chipGroup = binding.searchProximityChipGroup

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            if(chip.isChecked) {
                selectedProximityList.add(chip.text.toString())
            }
        }
        return selectedProximityList
    }

    private fun getSelectedAmenities() {
        val selectedStringsAmenities: List<String>? = getSelectedStringsProximity()
        selectedStringsAmenities?.forEach { amenity ->
            when (amenity) {
                "School" -> schoolAmenitie = true
                "Restaurants" -> restaurantsAmenitie = true
                "Playground" -> playgroundAmenitie = true
                "Shopping Area" -> shoppingAreaAmenitie = true
                "Supermarket" -> supermarketAmenitie = true
                "Cinema" -> cinemaAmenitie = true
            }
        }
    }

    private fun getSelectedNumbersOfPicturesListString() : List<String> {
        val selectedNumbersOfPictures = mutableListOf<String>()
        val chipGroup = binding.searchNumberOfPicturesChipGroup

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            if(chip.isChecked) {
                selectedNumbersOfPictures.add(chip.text.toString())
            }
        }
        return selectedNumbersOfPictures
    }

    private fun getSelectedNumberOfPictures(): List<Int>? {
        val selectedStringsPictures:  List<String>? = getSelectedNumbersOfPicturesListString()
        val selectedIntPictures = mutableListOf<Int>()

        selectedStringsPictures?.let { pictures ->
            val picturesValues = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")

            for (picturesValue in picturesValues) {
                if (pictures.contains(picturesValue)) {
                    selectedIntPictures.add(picturesValue.toInt())
                }
            }
        }
        return selectedIntPictures
    }

    private fun getAvailabilityChoice() : Boolean? {
        var availability : Boolean? = false
        binding.searchAvailabilityChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedChip : Chip? = group.findViewById(checkedId)
            if(selectedChip != null) {
                val choice: String = selectedChip.text.toString()
                availability = when (choice) {
                    "Available" -> true
                    "Sold" -> false
                    else -> true
                }
            }
        }
        return availability
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = formatDate(selectedYear, selectedMonth, selectedDay)
            editText.setText(formattedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun fetchAgentsListIntoSpinner(agents: List<Agent>) {
        val spinner = binding.searchAgentSpinner
        val adapter : ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, agents.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if(spinner != null) {
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedAgent = agents[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    selectedAgent = null
                }
            }
        }
    }

}