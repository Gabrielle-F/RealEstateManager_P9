package com.openclassrooms.realestatemanager.repository

import com.openclassrooms.realestatemanager.database.AgentDao
import com.openclassrooms.realestatemanager.model.Agent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentRepository @Inject constructor(private val agentDao : AgentDao) {

    fun createAgent(agent : Agent) = agentDao.insertAgent(agent)
}