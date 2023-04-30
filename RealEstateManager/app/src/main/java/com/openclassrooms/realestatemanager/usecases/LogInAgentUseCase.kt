package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.repository.AgentRepository
import javax.inject.Inject

class LogInAgentUseCase @Inject constructor(private val agentRepository: AgentRepository) {

    suspend fun invoke(email: String, password: String) : Boolean {
        return agentRepository.logIn(email, password)
    }
}