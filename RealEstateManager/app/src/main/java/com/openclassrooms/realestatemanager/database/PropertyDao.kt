package com.openclassrooms.realestatemanager.database

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
    fun getPropertyById(id : Int) : Flow<Property>

    @Query("SELECT * FROM property_table WHERE price BETWEEN :minPrice AND :maxPrice " +
            "AND area BETWEEN :minArea AND :maxArea " +
            "AND city = :city " +
            "AND type IN (:types) " +
            "AND rooms IN (:rooms) " +
            "AND sold = :availability " +
            "AND register_date >= :startDate AND register_date <= :endDate " +
            "AND sold_date >= :startDate AND sold_date <= :endDate " +
            "AND number_of_pictures IN (:numberOfPictures) " +
            "AND agent_id = :agentId " +
            "AND (school = :school OR restaurants = :restaurants " +
            "OR playground = :playground OR supermarket = :supermarket " +
            "OR shopping_area = :shoppingArea OR cinema = :cinema)")
    fun getSearchProperties(
        minPrice: Int, maxPrice: Int, minArea: Int, maxArea: Int, city: String?,
        types: List<String>?, rooms:List<Int>?,
        availability: Boolean?, startDate: String?, endDate: String?, numberOfPictures: List<Int>,
        agentId: Int?, school: Boolean, restaurants: Boolean, playground: Boolean,
        supermarket: Boolean, shoppingArea: Boolean, cinema: Boolean
    ): Flow<List<Property>>
}