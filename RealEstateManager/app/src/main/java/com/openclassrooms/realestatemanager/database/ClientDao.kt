package com.openclassrooms.realestatemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.openclassrooms.realestatemanager.model.Client

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun createClient(client : Client)
}