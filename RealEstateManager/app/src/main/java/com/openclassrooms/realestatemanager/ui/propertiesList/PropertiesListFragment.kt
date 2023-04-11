package com.openclassrooms.realestatemanager.ui.propertiesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentListPropertiesBinding
import com.openclassrooms.realestatemanager.model.Property
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PropertiesListFragment : Fragment(R.layout.fragment_list_properties) {


    private lateinit var binding : FragmentListPropertiesBinding
    private val propertiesListViewModel : PropertiesListViewModel by viewModels()
    private lateinit var adapter: PropertiesRecyclerViewAdapter
    private lateinit var propertiesList : List<Property>

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListPropertiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            fetchPropertiesList()
        }

        propertiesList = ArrayList()
        adapter = PropertiesRecyclerViewAdapter(propertiesList)
    }

    private suspend fun fetchPropertiesList() {
        propertiesListViewModel.getProperties()
        propertiesListViewModel.propertiesLiveData.observe(viewLifecycleOwner) { propertiesList ->
            adapter.updatePropertiesList(propertiesList)
        }
    }
}