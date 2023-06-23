package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalPicture(
    @PrimaryKey
    var imageUrl: String,
    var imageTitle: String?,
    var imageDescription: String?
) {
    constructor() : this("", "", "")
}
