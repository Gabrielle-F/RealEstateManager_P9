package com.openclassrooms.realestatemanager.model

import androidx.room.Embedded
import androidx.room.Relation

class CompleteProperty {

    @Embedded
    lateinit var property : Property

    @Relation(parentColumn = "id", entityColumn = "propertyId")
    var images : List<Image> = arrayListOf()
}
