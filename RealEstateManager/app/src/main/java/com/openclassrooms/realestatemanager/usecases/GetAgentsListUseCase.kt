package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.repository.AgentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAgentsListUseCase @Inject constructor(private val agentRepository: AgentRepository) {

    fun invoke() : Flow<List<Agent>> = agentRepository.getAllAgents()
}