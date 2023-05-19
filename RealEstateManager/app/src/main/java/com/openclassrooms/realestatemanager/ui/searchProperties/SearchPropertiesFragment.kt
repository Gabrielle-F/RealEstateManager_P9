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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchPropertiesBinding
import com.openclassrooms.realestatemanager.model.Agent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SearchPropertiesFragment : BottomSheetDialogFragment(R.layout.fragment_search_properties) {

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
    private lateinit var selectedAgent : Agent

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
        binding.searchStartDateEditTxt.setOnClickListener {
            showDatePickerDialog(binding.searchStartDateEditTxt)
        }
        binding.searchEndDateEditTxt.setOnClickListener {
            showDatePickerDialog(binding.searchEndDateEditTxt)
        }
    }

    private fun getMinAndMaxPrice() {
        binding.searchRangeSliderPrice.addOnChangeListener { slider, value, fromUser ->
            minPrice = slider.values[0].toInt()
            maxPrice = slider.values[1].toInt()
        }
    }

    private fun getMinAndMaxArea() {
        binding.searchRangeSliderArea.addOnChangeListener { slider, value, fromUser ->
            minArea = slider.values[0].toInt()
            maxArea = slider.values[1].toInt()
        }
    }

    private fun getSelectedTypes() : List<String>? {
        var selectedTypesList : List<String>? = listOf()
        binding.searchTypeChipGroup.setOnCheckedChangeListener{ group, checkedId ->
            val selectedTypes = mutableListOf<String>()
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as? Chip
                if(chip != null && chip.isSelected) {
                    selectedTypes.add(chip.text.toString())
                }
            }
            selectedTypesList = selectedTypes
        }
        return selectedTypesList
    }

    private fun getNumberOfRooms() : List<String>? {
        var selectedNumberOfRooms : List<String>? = listOf()
        binding.searchRoomsChipGroup.setOnCheckedChangeListener{ group, checkedId ->
            val selectedNumOfRooms = mutableListOf<String>()
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as? Chip
                if(chip != null && chip.isSelected) {
                    selectedNumOfRooms.add(chip.text.toString())
                }
            }
            selectedNumberOfRooms = selectedNumOfRooms
        }
        return selectedNumberOfRooms
    }

    private fun getSelectedNumberOfRooms(): List<Int>? {
        val selectedStringRooms: List<String>? = getNumberOfRooms()
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

    private fun getSelectedStringsProximity() : List<String> {
        var selectedProximityList : List<String> = listOf()
        binding.searchProximityChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedTypes = mutableListOf<String>()
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as? Chip
                if(chip != null && chip.isSelected) {
                    selectedTypes.add(chip.text.toString())
                }
            }
            selectedProximityList = selectedTypes
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

    private fun getNumberOfPictures() : List<String>? {
        var selectedNumberOfPictures : List<String>? = listOf()
        binding.searchNumberOfPicturesChipGroup.setOnCheckedChangeListener{ group, checkedId ->
            val selectedNumOfPictures = mutableListOf<String>()
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as? Chip
                if(chip != null && chip.isSelected) {
                    selectedNumOfPictures.add(chip.text.toString())
                }
            }
            selectedNumberOfPictures = selectedNumOfPictures
        }
        return selectedNumberOfPictures
    }

    private fun getSelectedNumberOfPictures(): List<Int>? {
        val selectedStringsPictures:  List<String>? = getNumberOfPictures()
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

                }
            }
        }
    }

}