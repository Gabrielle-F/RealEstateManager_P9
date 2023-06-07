package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agent_table")
data class Agent(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val name: String
)

data class AgentFirestore(
    val id: String,
    val email: String,
    val name: String
)