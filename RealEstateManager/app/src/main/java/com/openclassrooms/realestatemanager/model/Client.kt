package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client_table")
data class Client(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val email : String,
    val name : String
)

data class ClientFirestore(
    val id : String,
    val email : String
)
