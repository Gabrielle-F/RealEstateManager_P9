package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "property_table")
data class Property(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "type")
    var type: String,
    @ColumnInfo(name = "price")
    var price: Int,
    @ColumnInfo(name = "address_street")
    var streetName: String,
    @ColumnInfo(name = "address_street_number")
    var streetNumber: String,
    @ColumnInfo(name = "area")
    var area: Int,
    @ColumnInfo(name = "rooms")
    var rooms: Int,
    @ColumnInfo(name = "postal_code")
    var postalCode: String,
    @ColumnInfo(name = "city")
    var city: String,
    @ColumnInfo(name = "sold")
    var sold: Boolean,
    @ColumnInfo(name = "register_date")
    var registerDate: String,
    @ColumnInfo(name = "sold_date")
    var soldDate: String?,
    @ColumnInfo(name = "school")
    var school: Boolean,
    @ColumnInfo(name = "restaurants")
    var restaurants: Boolean,
    @ColumnInfo(name = "playground")
    var playground: Boolean,
    @ColumnInfo(name = "supermarket")
    var supermarket: Boolean,
    @ColumnInfo(name = "shopping_area")
    var shoppingArea: Boolean,
    @ColumnInfo(name = "cinema")
    var cinema: Boolean,
    @ColumnInfo(name = "pictures")
    var pictures: List<Image>
) : Serializable