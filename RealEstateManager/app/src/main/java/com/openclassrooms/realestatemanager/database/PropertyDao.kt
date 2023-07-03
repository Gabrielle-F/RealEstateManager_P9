package com.openclassrooms.realestatemanager.database

import android.database.Cursor
import androidx.room.*
import com.openclassrooms.realestatemanager.model.Property
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProperty(property: Property)

    @Update
    suspend fun updateProperty(property: Property)

    @Query("SELECT * FROM property_table")
    fun getAllProperties() : Flow<List<Property>>

    @Query("SELECT * FROM property_table WHERE id = :id")
    fun getPropertyById(id : String) : Flow<Property>?

    @Query("SELECT * FROM property_table WHERE price BETWEEN :minPrice AND :maxPrice " +
            "OR area BETWEEN :minArea AND :maxArea " +
            "OR city = :city " +
            "OR type IN (:types) " +
            "OR rooms IN (:rooms) " +
            "OR sold = :availability " +
            "OR register_date >= :startDate AND register_date <= :endDate " +
            "OR sold_date >= :startDate AND sold_date <= :endDate " +
            "OR number_of_pictures IN (:numberOfPictures) " +
            "OR agent_name = :agentName " +
            "OR (school = :school OR restaurants = :restaurants " +
            "OR playground = :playground OR supermarket = :supermarket " +
            "OR shopping_area = :shoppingArea OR cinema = :cinema)")
    fun getSearchProperties(
        minPrice: Int, maxPrice: Int, minArea: Int, maxArea: Int, city: String?,
        types: List<String>?, rooms:List<Int>?,
        availability: Boolean?, startDate: String?, endDate: String?, numberOfPictures: List<Int>?,
        agentName: String?, school: Boolean, restaurants: Boolean, playground: Boolean,
        supermarket: Boolean, shoppingArea: Boolean, cinema: Boolean
    ): Flow<List<Property>>

    /**
     * For ContentProvider purpose
     */

    @Query("SELECT * FROM property_table WHERE id = :id")
    fun getPropertyWithCursor(id: Int): Cursor

    @Update
    suspend fun update(property: Property): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(property: Property) : Long
}