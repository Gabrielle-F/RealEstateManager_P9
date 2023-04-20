package com.openclassrooms.realestatemanager.model

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity
data class Image(
    @PrimaryKey
    var imageUri: String,
    var imageTitle: String?,
    var imageDescription: String?,
    var firstPicture: Boolean?,
)
