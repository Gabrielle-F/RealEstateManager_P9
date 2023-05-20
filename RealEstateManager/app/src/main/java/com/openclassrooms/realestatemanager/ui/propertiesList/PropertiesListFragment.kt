package com.openclassrooms.realestatemanager.ui.propertiesList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentListPropertiesBinding
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.propertyDetails.PropertyDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PropertiesListFragment : Fragment(R.layout.fragment_list_properties), PropertiesRecyclerViewAdapter.OnItemClickListener {


    private lateinit var binding : FragmentListPropertiesBinding
    private val propertiesListViewModel : PropertiesListViewModel by viewModels()
    private lateinit var propertiesAdapter: PropertiesRecyclerViewAdapter

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListPropertiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        propertiesAdapter = PropertiesRecyclerViewAdapter(this)
        binding.propertiesListRecyclerView.adapter = propertiesAdapter

        propertiesListViewModel.propertiesLiveData.observe(viewLifecycleOwner) { propertiesList ->
            Log.d("TAG", "Properties list size: ${propertiesList.size}")
            propertiesAdapter.updatePropertiesList(propertiesList)
        }
        propertiesListViewModel.searchProperties.observe(viewLifecycleOwner) { searchPropertiesList ->
            propertiesAdapter.updatePropertiesList(searchPropertiesList)
        }
    }

    @Override
    override fun onStart() {
        super.onStart()
        propertiesListViewModel.getPropertiesList()
    }

    fun updatePropertiesList() {
        lifecycleScope.launch {
            propertiesListViewModel.getProperties()
            propertiesListViewModel.propertiesLiveData.observe(viewLifecycleOwner) { propertiesList ->
                Log.d("TAG", "Properties list size: ${propertiesList.size}")
                propertiesAdapter.updatePropertiesList(propertiesList)
            }
        }
    }

    fun getFilteredList(minPrice: Int, maxPrice: Int, minArea: Int, maxArea:Int, city: String?,
                        types: List<String>?, rooms: List<Int>?, availability: Boolean?, startDate:
                        String?, endDate: String ?, numberOfPictures: List<Int>, agentId: Int?,
                        school: Boolean, restaurants: Boolean, playground: Boolean, supermarket: Boolean, shoppingArea: Boolean, cinema: Boolean) {
        propertiesListViewModel.updateFilteredList(minPrice, maxPrice, minArea, maxArea, city, types, rooms, availability, startDate, endDate,
            numberOfPictures, agentId, school, restaurants, playground, supermarket, shoppingArea, cinema)
    }

    override fun onClick(property: Property) {
        val propertyDetailsFragment = PropertyDetailsFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        val bundle = Bundle()
        bundle.putInt("selectedPropertyId", property.id)
        propertyDetailsFragment.arguments = bundle
        fragmentManager.beginTransaction()
            .replace(R.id.activity_main_fragment_container_view, propertyDetailsFragment)
            .addToBackStack(null)
            .commit()
    }
}