package com.openclassrooms.realestatemanager.repository

import com.openclassrooms.realestatemanager.database.ImageDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(private val imageDao: ImageDao) {

}