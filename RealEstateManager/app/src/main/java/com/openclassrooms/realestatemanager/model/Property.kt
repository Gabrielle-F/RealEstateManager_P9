package com.openclassrooms.realestatemanager.model

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.utils.Converters
import java.io.Serializable

@Entity(
    tableName = "property_table",
    foreignKeys = [ForeignKey(
        entity = Agent::class,
        parentColumns = ["id"],
        childColumns = ["agent_id"],
        onDelete = CASCADE
    )]
)
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
    var pictures: List<Image>,
    @ColumnInfo(name = "number_of_pictures")
    var numberOfPictures: Int,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "lat_lng")
    var latLng: LatLng?,
    @ColumnInfo(name = "agent_id", index = true)
    val agentId: Int
) : Serializable {

    fun getFirstImage(): Image? {
        return pictures.find { it.firstPicture == true }
    }

    fun fromContentValues(values: ContentValues): Property {
        val id = if (values.containsKey("id")) values.getAsInteger("id") else 0
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
        val latLng = if (values.containsKey("latLng")) values.getAsString("latLng") else ""
        val agentId = if (values.containsKey("agentId")) values.getAsInteger("agentId") else 0

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
            Converters().fromStringToLatLng(latLng),
            agentId
        )
    }

}

data class PropertyFirestore(
    var id: String,
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
    var picturesUri: List<String>,
    var numberOfPictures: Int,
    var description: String,
    var latLng: LatLng?,
    var agentId: Int,
    var agentName: String
)