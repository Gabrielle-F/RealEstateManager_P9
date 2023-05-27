package com.openclassrooms.realestatemanager.ui.propertiesMap

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentMapBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    interface ShowMapListener {
        fun onMapReadyToShow(showMap: Boolean)
    }

    private lateinit var mapView : SupportMapFragment
    private val ACCESS_FINE_LOCATION_CODE = 125
    private val ACCESS_COARSE_LOCATION_CODE = 120
    private val ACCESS_WIFI_STATE_CODE = 115
    private val viewModel : MapViewModel by viewModels()
    private lateinit var binding : FragmentMapBinding
    private var map : GoogleMap? = null
    private lateinit var userLatLng : LatLng
    private lateinit var onMapReadyToShowListener: ShowMapListener
    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val internetPermission = arrayOf(
        Manifest.permission.ACCESS_WIFI_STATE
    )

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
        mapView.getMapAsync(this)

        checkAccessFineAndCoarseLocationPermission()
        checkAccessWifiStatePermission()

        getUserLocation()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ShowMapListener) {
            onMapReadyToShowListener = context
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap

        shouldShowMap()

        viewModel.propertiesLiveData.observe(viewLifecycleOwner) { propertiesList ->
            map?.let { map ->
                propertiesList.forEach { property ->
                    property.latLng?.let {
                        map.addMarker(MarkerOptions().position(it)).also { marker ->
                            marker.tag = property.id
                        }
                    }
                }
            }
        }
    }

    private fun shouldShowMap() {
        var showMap = false
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PermissionChecker.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PermissionChecker.PERMISSION_GRANTED) {
            checkAccessFineAndCoarseLocationPermission()
            checkAccessWifiStatePermission()
        } else {
            map?.isMyLocationEnabled = true
            showMap = true
        }

        when(showMap.and(Utils.isInternetAvailable(context))) {
            true -> showActiveMap()
            false -> showInactiveMap()
        }
    }

    private fun showActiveMap() {
        childFragmentManager.beginTransaction().show(mapView).commit()
        binding.errorState.visibility = View.INVISIBLE
        binding.retryButton.visibility = View.INVISIBLE
    }

    private fun showInactiveMap() {
        childFragmentManager.beginTransaction().show(mapView).commit()
        binding.errorState.visibility = View.VISIBLE
        binding.retryButton.visibility = View.VISIBLE
    }

    private fun checkAccessFineAndCoarseLocationPermission() {
        EasyPermissions.requestPermissions(
            requireActivity(),
            getString(R.string.fine_and_coarse_location),
            ACCESS_FINE_LOCATION_CODE, *locationPermissions)
    }

    private fun checkAccessWifiStatePermission() {
        EasyPermissions.requestPermissions(
            requireActivity(),
            getString(R.string.wifi_state),
            ACCESS_WIFI_STATE_CODE, *internetPermission)
    }

    private fun getUserLocation() : LatLng? {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                if(location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    userLatLng = LatLng(latitude, longitude)
                }
            }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "No location found !", Toast.LENGTH_SHORT).show()
                }
        }
        return userLatLng
    }

}