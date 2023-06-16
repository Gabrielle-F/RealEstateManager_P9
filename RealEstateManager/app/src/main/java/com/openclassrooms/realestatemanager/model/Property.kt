package com.openclassrooms.realestatemanager.model

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.utils.Converters
import java.io.Serializable

@Entity(tableName = "property_table")
data class Property(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String = "",
    @ColumnInfo(name = "type")
    var type: String = "",
    @ColumnInfo(name = "price")
    var price: Int = 0,
    @ColumnInfo(name = "address_street")
    var streetName: String = "",
    @ColumnInfo(name = "address_street_number")
    var streetNumber: String = "",
    @ColumnInfo(name = "area")
    var area: Int = 0,
    @ColumnInfo(name = "rooms")
    var rooms: Int = 0,
    @ColumnInfo(name = "postal_code")
    var postalCode: String = "",
    @ColumnInfo(name = "city")
    var city: String = "",
    @ColumnInfo(name = "sold")
    var sold: Boolean = false,
    @ColumnInfo(name = "register_date")
    var registerDate: String = "",
    @ColumnInfo(name = "sold_date")
    var soldDate: String? = "",
    @ColumnInfo(name = "school")
    var school: Boolean = false,
    @ColumnInfo(name = "restaurants")
    var restaurants: Boolean = false,
    @ColumnInfo(name = "playground")
    var playground: Boolean = false,
    @ColumnInfo(name = "supermarket")
    var supermarket: Boolean = false,
    @ColumnInfo(name = "shopping_area")
    var shoppingArea: Boolean = false,
    @ColumnInfo(name = "cinema")
    var cinema: Boolean = false,
    @ColumnInfo(name = "pictures")
    var pictures: List<LocalPicture>  = emptyList(),
    @ColumnInfo(name = "number_of_pictures")
    var numberOfPictures: Int = 0,
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "latitude")
    var latitude: Double = 0.0,
    @ColumnInfo(name = "longitude")
    var longitude: Double = 0.0,
    @ColumnInfo(name = "agent_name")
    var agentName: String = ""
) : Serializable {

    fun fromContentValues(values: ContentValues): Property {
        val id = if (values.containsKey("id")) values.getAsString("id") else ""
        val type = if (values.containsKey("type")) values.getAsString("type") else ""
        val price = if (values.containsKey("price")) values.getAsInteger("price") else 0
        val streetName =
            if (values.containsKey("streetName")) values.getAsString("streetName") else ""
        val streetNumber =
            if (values.containsKey("streetNumber")) values.getAsString("streetNumber") else ""
        val area = if (values.containsKey("area")) values.getAsInteger("area") else 0
        val rooms = if (values.containsKey("rooms")) values.getAsInteger("rooms") else 0
        val postalCode =
            if (values.containsKey("postalCode")) values.getAsString("postalCode") else ""
        val city = if (values.containsKey("city")) values.getAsString("city") else ""
        val sold = if (values.containsKey("sold")) values.getAsBoolean("sold") else false
        val registerDate =
            if (values.containsKey("registerDate")) values.getAsString("registerDate") else ""
        val soldDate = if (values.containsKey("soldDate")) values.getAsString("soldDate") else ""
        val school = if (values.containsKey("school")) values.getAsBoolean("school") else false
        val restaurants =
            if (values.containsKey("restaurants")) values.getAsBoolean("restaurants") else false
        val playground =
            if (values.containsKey("playground")) values.getAsBoolean("playground") else false
        val supermarket =
            if (values.containsKey("supermarket")) values.getAsBoolean("supermarket") else false
        val shoppingArea =
            if (values.containsKey("shoppingArea")) values.getAsBoolean("shoppingArea") else false
        val cinema = if (values.containsKey("cinema")) values.getAsBoolean("cinema") else false
        val pictures = if (values.containsKey("pictures")) values.getAsString("pictures") else ""
        val numberOfPictures =
            if (values.containsKey("numberOfPictures")) values.getAsInteger("numberOfPictures") else 0
        val description =
            if (values.containsKey("description")) values.getAsString("description") else ""
        val latitude = if (values.containsKey("latitude")) values.getAsDouble("latitude") else 0.0
        val longitude = if (values.containsKey("longitude")) values.getAsDouble("longitude") else 0.0
        val agentName = if (values.containsKey("agentName")) values.getAsString("agentId") else ""

        return Property(
            id,
            type,
            price,
            streetName,
            streetNumber,
            area,
            rooms,
            postalCode,
            city,
            sold,
            registerDate,
            soldDate,
            school,
            restaurants,
            playground,
            supermarket,
            shoppingArea,
            cinema,
            Converters().toImages(pictures),
            numberOfPictures,
            description,
            latitude,
            longitude,
            agentName
        )
    }
}

data class PropertyFirestore(
    var type: String,
    var price: Int,
    var streetName: String,
    var streetNumber: String,
    var area: Int,
    var rooms: Int,
    var postalCode: String,
    var city: String,
    var sold: Boolean,
    var registerDate: String,
    var soldDate: String?,
    var school: Boolean,
    var restaurants: Boolean,
    var playground: Boolean,
    var supermarket: Boolean,
    var shoppingArea: Boolean,
    var cinema: Boolean,
    var pictures: List<LocalPicture>,
    var numberOfPictures: Int,
    var description: String,
    var latitude: Double,
    var longitude: Double,
    var agentId: String,
)

