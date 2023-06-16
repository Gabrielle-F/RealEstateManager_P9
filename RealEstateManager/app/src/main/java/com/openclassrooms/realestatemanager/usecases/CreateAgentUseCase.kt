package com.openclassrooms.realestatemanager.usecases

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.repository.AgentRepository
import javax.inject.Inject

class CreateAgentUseCase @Inject constructor(private val agentRepository: AgentRepository) {

    suspend fun createAgent(agent: Agent) = agentRepository.createAgent(agent)

    suspend fun createAgentWithEmailAndPassword(email: String, password: String): Boolean =
        agentRepository.createUserWithEmailAndPassword(email, password)

    fun createAgentInFirestoreDatabase(firebaseUser: FirebaseUser?, name: String): String = agentRepository.createAgentInFirestoreDatabase(firebaseUser, name)
}