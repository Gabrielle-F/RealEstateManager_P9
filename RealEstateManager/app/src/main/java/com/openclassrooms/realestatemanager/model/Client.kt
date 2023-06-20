package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client_table")
data class Client(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "name")
    val name: String
)

data class ClientFirestore(
    val email: String,
    val name: String
)
