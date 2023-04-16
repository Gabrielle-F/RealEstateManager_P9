package com.openclassrooms.realestatemanager.repository

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.openclassrooms.realestatemanager.database.ImageDao
import com.openclassrooms.realestatemanager.model.ExternalStorageImage
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.utils.sdk29AndUp
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(private val imageDao: ImageDao) {

    suspend fun insertImageInLocalDatabase(image : Image) = imageDao.insertImages(image)

    suspend fun loadPicturesFromExternalStorage(context: Context) : List<ExternalStorageImage> {
        return with(Dispatchers.IO) {
            val collection = sdk29AndUp {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.HEIGHT,
                MediaStore.Images.Media.DATE_ADDED
            )

            val photos = mutableListOf<ExternalStorageImage>()
            context.contentResolver.query(
                collection,
                projection,
                null,
                null,
                "${MediaStore.Images.Media.DATE_ADDED} DESC"
            )?.use {
                    cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
                val heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val displayName = cursor.getString(displayNameColumn)
                    val width = cursor.getInt(widthColumn)
                    val height = cursor.getInt(heightColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    photos.add(ExternalStorageImage(id, displayName, width, height, contentUri))
                }
                photos.toList()
            }?: listOf()
        }
    }

}