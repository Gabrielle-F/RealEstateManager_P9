package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL

@Entity
data class LocalPicture(
    @PrimaryKey
    var imageUrl: String,
    var imageTitle: String?,
    var imageDescription: String?,
)

data class PictureFirestore(
    var imageUrl: URL,
    var imageTitle: String?,
    var imageDescription: String?
)
