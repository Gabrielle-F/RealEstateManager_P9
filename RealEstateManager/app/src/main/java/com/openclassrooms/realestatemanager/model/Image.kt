package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "image_table", foreignKeys = [ForeignKey(entity = Property::class,
    parentColumns = ["id"],
    childColumns = ["property_id"])])
data class Image(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("img_path")
    var imagePath: String,
    @ColumnInfo("img_title")
    var imageTitle: String,
    @ColumnInfo("img_description")
    var imageDescription: String,
    @ColumnInfo("property_id")
    var propertyId: Int
)
