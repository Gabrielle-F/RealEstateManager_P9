package com.openclassrooms.realestatemanager.ui.propertiesList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.DaggerHiltApplication
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentListPropertiesBinding
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.propertiesMap.MapFragment
import com.openclassrooms.realestatemanager.ui.propertyDetails.PropertyDetailsFragment
import com.openclassrooms.realestatemanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertiesListFragment : Fragment(R.layout.activity_main),
    PropertiesRecyclerViewAdapter.OnItemClickListener {


    private lateinit var binding: FragmentListPropertiesBinding
    private val propertiesListViewModel: PropertiesListViewModel by viewModels()
    private lateinit var propertiesAdapter: PropertiesRecyclerViewAdapter
    private lateinit var mapFragment: MapFragment
    private var internetAvailable: Boolean = true
    private var isTablet: Boolean = false

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListPropertiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        internetAvailable = Utils.isInternetAvailable(requireContext())
        isTablet = resources.getBoolean(R.bool.isTablet)
        mapFragment = MapFragment()
        binding.mapContainer?.id?.let {
            childFragmentManager.beginTransaction()
                .add(it, mapFragment).commit()
        }

        propertiesAdapter = PropertiesRecyclerViewAdapter(this)
        binding.propertiesListRecyclerView.adapter = propertiesAdapter

        propertiesListViewModel.propertiesLD.observe(viewLifecycleOwner) { propertiesList ->
            Log.d("TAG", "Properties list size: ${propertiesList.size}")
            propertiesAdapter.updatePropertiesList(propertiesList)
        }
        propertiesListViewModel.searchProperties.observe(viewLifecycleOwner) { searchPropertiesList ->
            Log.d("TAG", "SearchProperties list size : ${searchPropertiesList.size}")
            propertiesAdapter.updatePropertiesList(searchPropertiesList)
        }
    }

    @Override
    override fun onStart() {
        super.onStart()
        propertiesListViewModel.getAllProperties()
    }

    fun getCompleteList() {
        propertiesListViewModel.getAllProperties()
    }

    fun getFilteredList(
        minPrice: Int,
        maxPrice: Int,
        minArea: Int,
        maxArea: Int,
        city: String?,
        types: List<String>?,
        rooms: List<Int>?,
        availability: Boolean?,
        startDate:
        String?,
        endDate: String?,
        numberOfPictures: List<Int>,
        agentName: String?,
        school: Boolean,
        restaurants: Boolean,
        playground: Boolean,
        supermarket: Boolean,
        shoppingArea: Boolean,
        cinema: Boolean
    ) {
        propertiesListViewModel.getFilteredList(
            minPrice,
            maxPrice,
            minArea,
            maxArea,
            city,
            types,
            rooms,
            availability,
            startDate,
            endDate,
            numberOfPictures,
            agentName,
            school,
            restaurants,
            playground,
            supermarket,
            shoppingArea,
            cinema
        )
    }

    override fun onClick(property: Property) {
        val propertyDetailsFragment = PropertyDetailsFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        val bundle = Bundle()
        bundle.putString("selectedPropertyId", property.id)
        propertyDetailsFragment.arguments = bundle

        if(isTablet) {
            fragmentManager.beginTransaction().replace(R.id.fragment_view_details_property, propertyDetailsFragment).commit()
        } else {
            fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, propertyDetailsFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getSharedPreferences(): String {
        val daggerHilt = requireActivity().application as DaggerHiltApplication
        var selectedCurrency: String = daggerHilt.appPreferences.getSelectedCurrency()
        return selectedCurrency
    }

    fun updateCurrency(currency: String) {
        propertiesAdapter.updateSelectedCurrency(currency)
    }
}