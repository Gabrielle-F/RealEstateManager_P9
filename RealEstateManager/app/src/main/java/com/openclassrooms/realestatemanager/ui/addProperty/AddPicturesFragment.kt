package com.openclassrooms.realestatemanager.ui.addProperty

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentAddImagePopUpBinding
import com.vmadalin.easypermissions.EasyPermissions

class AddPicturesFragment : Fragment(R.layout.fragment_add_image_pop_up) {

    private lateinit var binding : FragmentAddImagePopUpBinding
    private val EXTERNAL_STORAGE_PERMISSION_CODE  : Int = 20
    private val CAMERA_PERMISSION_CODE : Int = 10
    private val cameraPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val externalStoragePermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddImagePopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkExternalStoragePermissions()
        checkCameraPermissions()

        configureListeners()
    }

    private fun configureListeners() {
        binding.addPictureFragmentTakePictureBtn.setOnClickListener {
            //TODO start PictureDescriptionFragment with parameter
        }
        binding.addPictureFragmentChooseInAlbumBtn.setOnClickListener {
            //TODO start PictureDescriptionFragment with parameter
        }
    }

    private fun checkExternalStoragePermissions() {
        EasyPermissions.requestPermissions(
            requireParentFragment(),
            getString(R.string.camera_and_storage),
            EXTERNAL_STORAGE_PERMISSION_CODE, *externalStoragePermissions
        )
    }

    private fun checkCameraPermissions() {
        EasyPermissions.requestPermissions(
            requireParentFragment(),
            getString(R.string.camera_and_storage),
            CAMERA_PERMISSION_CODE, *cameraPermissions
        )
    }
}