package com.openclassrooms.realestatemanager.ui.addProperty

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentAddImagePopUpBinding
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

@AndroidEntryPoint
class AddPicturesFragment : Fragment(R.layout.fragment_add_image_pop_up) {

    @ApplicationContext private lateinit var context : Context
    private val contentResolver = context.contentResolver
    private lateinit var binding : FragmentAddImagePopUpBinding
    private lateinit var imagePath: String
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
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_CODE)
        }
        binding.addPictureFragmentChooseInAlbumBtn.setOnClickListener {
            val selectPictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(selectPictureIntent, REQUEST_IMAGE_SELECT_CODE)
        }
        binding.addPictureFragmentSaveBtn.setOnClickListener {
            sendDataToAddPropertyActivity()
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
                        imagePath = convertUriToString(imageUri)
                    }
                }
                REQUEST_IMAGE_SELECT_CODE -> {
                    if(data != null) {
                        val imageUri = data.data
                        val imageView = binding.addPictureFragmentSeeResult
                        Glide.with(this).load(imageUri).into(imageView)
                        imagePath = convertUriToString(imageUri)
                    }
                }
            }
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

    private fun sendDataToAddPropertyActivity() {
        val imageName = binding.addPictureFragmentNamePicture.text.toString()
        val imageDescription = binding.addPictureFragmentDescribePicture.text.toString()
        val firstPicture = defineAsFirstPictureCheckboxIsCheckedOrNot()
        val intent = Intent(getContext(), AddPropertyActivity::class.java)
        intent.putExtra("ImageName", imageName)
        intent.putExtra("ImageDescription", imageDescription)
        intent.putExtra("ImagePath", imagePath)
        intent.putExtra("DefineAsFirstImage", firstPicture)
    }

    private fun convertUriToString(uriValue: Uri?) : String {
        val stringToReturn = uriValue.toString()
        return stringToReturn
    }

    private fun defineAsFirstPictureCheckboxIsCheckedOrNot() : Boolean {
        val firstPicture : Boolean
        val firstPictureCheckbox : CheckBox = binding.addPictureFragmentDefineAsFirstPictureCheckbox
        firstPicture = firstPictureCheckbox.isChecked
        return firstPicture
    }
}