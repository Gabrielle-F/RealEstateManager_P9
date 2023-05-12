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
    private lateinit var adapter: PropertiesRecyclerViewAdapter

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListPropertiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PropertiesRecyclerViewAdapter(this)
        binding.propertiesListRecyclerView.adapter = adapter

        lifecycleScope.launch {
            fetchPropertiesList()
        }
    }

    @Override
    override fun onStart() {
        super.onStart()
    }

    private suspend fun fetchPropertiesList() {
        propertiesListViewModel.propertiesLiveData.observe(viewLifecycleOwner) { propertiesList ->
            Log.d("TAG", "Properties list size: ${propertiesList.size}")
            adapter.updatePropertiesList(propertiesList)
        }
        propertiesListViewModel.getProperties()
    }

    fun updatePropertiesList() {
        lifecycleScope.launch {
            propertiesListViewModel.getProperties()
            propertiesListViewModel.propertiesLiveData.observe(viewLifecycleOwner) { propertiesList ->
                Log.d("TAG", "Properties list size: ${propertiesList.size}")
                adapter.updatePropertiesList(propertiesList)
            }
        }
    }

    override fun onClick(property: Property) {
        val propertyDetailsFragment = PropertyDetailsFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        val bundle = Bundle()
        bundle.putSerializable("selectedProperty", property)
        propertyDetailsFragment.arguments = bundle
        fragmentManager.beginTransaction().show(propertyDetailsFragment).commit()
    }
}