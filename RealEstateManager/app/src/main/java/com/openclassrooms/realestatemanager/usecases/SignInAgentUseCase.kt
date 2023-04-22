package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.repository.AgentRepository
import javax.inject.Inject

class SignInAgentUseCase @Inject constructor(private val agentRepository: AgentRepository) {

    suspend fun signIn(email: String, password: String) = agentRepository.signIn(email, password)
}