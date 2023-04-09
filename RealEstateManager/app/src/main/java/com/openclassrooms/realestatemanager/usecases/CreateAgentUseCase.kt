package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.repository.AgentRepository
import javax.inject.Inject

class CreateAgentUseCase @Inject constructor(private val agentRepository: AgentRepository) {

    fun createAgent(agent : Agent) = agentRepository.createAgent(agent)
}