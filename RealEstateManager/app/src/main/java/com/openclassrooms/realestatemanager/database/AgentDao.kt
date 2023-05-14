package com.openclassrooms.realestatemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.model.Agent
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertAgent(agent : Agent)

    @Query("SELECT * FROM agent_table")
    fun getAllAgents() : Flow<List<Agent>>

    @Query("SELECT * FROM agent_table WHERE id = :id")
    fun getAgentById(id : Int) : Flow<Agent>

}