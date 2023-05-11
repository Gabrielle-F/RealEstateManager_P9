package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Image(
    @PrimaryKey
    var imageUri: String,
    var imageTitle: String?,
    var imageDescription: String?,
    var firstPicture: Boolean?,
)
