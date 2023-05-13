package com.openclassrooms.realestatemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.model.Property
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProperty(property: Property)

    @Query("SELECT * FROM property_table")
    fun getAllProperties() : Flow<List<Property>>

    @Query("SELECT * FROM property_table WHERE id = :id")
    fun getPropertyById(id : Int) : Flow<Property>
}