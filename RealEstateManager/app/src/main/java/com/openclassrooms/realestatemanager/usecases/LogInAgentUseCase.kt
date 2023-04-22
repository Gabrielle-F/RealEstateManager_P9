package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.repository.AgentRepository
import javax.inject.Inject

class LogInAgentUseCase @Inject constructor(private val agentRepository: AgentRepository) {

    suspend fun logIn(email: String, password: String) = agentRepository.logIn(email, password)
}