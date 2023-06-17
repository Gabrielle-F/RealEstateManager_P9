package com.openclassrooms.realestatemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.model.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun createClient(client: Client)

    /**
     * For tests purpose
     */
    @Query("SELECT * FROM client_table")
    fun getAllClients(): Flow<List<Client>>

    @Query("SELECT * FROM client_table WHERE id = :id")
    fun getClientById(id: String): Flow<Client>
}