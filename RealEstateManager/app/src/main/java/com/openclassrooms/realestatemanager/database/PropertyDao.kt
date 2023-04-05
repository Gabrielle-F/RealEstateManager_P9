package com.openclassrooms.realestatemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.model.Property

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProperty(property: Property)

    @Query("SELECT * FROM Property")
    fun getAllProperties()
}