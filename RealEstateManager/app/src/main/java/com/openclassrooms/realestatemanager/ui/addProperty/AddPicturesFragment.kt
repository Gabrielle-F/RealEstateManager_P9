package com.openclassrooms.realestatemanager.ui.addProperty

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentAddImageBinding
import com.openclassrooms.realestatemanager.model.Image
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL

@AndroidEntryPoint
class AddPicturesFragment : BottomSheetDialogFragment(R.layout.fragment_add_image) {

    private lateinit var context : Context
    private lateinit var listener : OnDataChangeListener
    private lateinit var binding : FragmentAddImageBinding
    private lateinit var imagePath: String
    private lateinit var imageUrl: URL
    private val EXTERNAL_STORAGE_PERMISSION_CODE  : Int = 20
    private val CAMERA_PERMISSION_CODE : Int = 10
    private val REQUEST_IMAGE_CAPTURE_CODE : Int = 100
    private val REQUEST_IMAGE_SELECT_CODE : Int = 200
    private val cameraPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val externalStoragePermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    interface OnDataChangeListener {
        fun getImage(image: Image)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context = requireContext()

        checkExternalStoragePermissions()
        checkCameraPermissions()

        configureListeners()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is OnDataChangeListener) {
            listener = context
        }
    }

    private fun configureListeners() {
        binding.addPictureFragmentTakePictureBtn.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_CODE)
        }
        binding.addPictureFragmentChooseInAlbumBtn.setOnClickListener {
            val selectPictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(selectPictureIntent, REQUEST_IMAGE_SELECT_CODE)
        }
        binding.addPictureFragmentSaveBtn.setOnClickListener {
            sendDataToAddPropertyActivity()
            dismiss()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_IMAGE_CAPTURE_CODE -> {
                    if(data != null) {
                        val imageUri = data.data
                        val imageView = binding.addPictureFragmentSeeResult
                        Glide.with(this).load(imageUri).into(imageView)
                        imagePath = imageUri.toString()
                    }
                }
                REQUEST_IMAGE_SELECT_CODE -> {
                    if(data != null) {
                        val imageUri = data.data
                        val imageView = binding.addPictureFragmentSeeResult
                        Glide.with(this).load(imageUri).into(imageView)
                        imagePath = imageUri.toString()
                    }
                }
            }
        }
    }

    private fun checkExternalStoragePermissions() {
        EasyPermissions.requestPermissions(
            requireActivity(),
            getString(R.string.camera_and_storage),
            EXTERNAL_STORAGE_PERMISSION_CODE, *externalStoragePermissions
        )
    }

    private fun checkCameraPermissions() {
        EasyPermissions.requestPermissions(
            requireActivity(),
            getString(R.string.camera_and_storage),
            CAMERA_PERMISSION_CODE, *cameraPermissions
        )
    }

    private fun sendDataToAddPropertyActivity() {
        if(validateFields()) {
            val imageName = binding.addPictureFragmentNamePicture.text.toString()
            val imageDescription = binding.addPictureFragmentDescribePicture.text.toString()
            val firstPicture = defineAsFirstPictureCheckboxIsCheckedOrNot()

            val imageToCreate = Image(imagePath, imageName, imageDescription, firstPicture)

            listener.getImage(imageToCreate)
        }
    }

    private fun defineAsFirstPictureCheckboxIsCheckedOrNot() : Boolean {
        val bundle = arguments
        val hasFirstPicture = bundle?.getBoolean("hasFirstPicture") ?: false
        val firstPicture : Boolean
        val firstPictureCheckbox : CheckBox = binding.addPictureFragmentDefineAsFirstPictureCheckbox
        firstPictureCheckbox.isEnabled = !hasFirstPicture
        firstPicture = firstPictureCheckbox.isChecked
        return firstPicture
    }

    private fun validateFields() : Boolean {
        val imageName = binding.addPictureFragmentNamePicture.text.toString()
        val imageDescription = binding.addPictureFragmentDescribePicture.text.toString()
        if(imageName.isBlank()) {
            Toast.makeText(context, "Please enter a name !", Toast.LENGTH_SHORT).show()
            return false
        }
        if(imageDescription.isBlank()) {
            Toast.makeText(context, "Please enter a description !", Toast.LENGTH_SHORT).show()
            return false
        }
        if(imagePath.isBlank()) {
            Toast.makeText(context, "Please select a picture to add !", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}