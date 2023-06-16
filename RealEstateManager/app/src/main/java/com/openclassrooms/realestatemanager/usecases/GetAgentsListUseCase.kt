package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.repository.AgentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetAgentsListUseCase @Inject constructor(private val agentRepository: AgentRepository) {
    fun invoke() : Flow<List<Agent>> = agentRepository.getAllAgents()

    fun invokeAgentsList(): Flow<List<Agent>> {
        return agentRepository.getAgentsList().onEach {
            for (agent: Agent in it) {
                agentRepository.createAgent(agent)
            }
        }
    }
}