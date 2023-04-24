package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.repository.AgentRepository
import javax.inject.Inject

class CreateAgentUseCase @Inject constructor(private val agentRepository: AgentRepository) {

    suspend fun createAgent(agent : Agent) = agentRepository.createAgent(agent)

    suspend fun createAgentWithEmailAndPassword(email: String, password: String, name: String) = agentRepository.createUserWithEmailAndPassword(email, password, name)
}