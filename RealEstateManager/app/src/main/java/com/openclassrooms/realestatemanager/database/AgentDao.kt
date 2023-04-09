package com.openclassrooms.realestatemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.openclassrooms.realestatemanager.model.Agent

@Dao
interface AgentDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertAgent(agent : Agent)
}