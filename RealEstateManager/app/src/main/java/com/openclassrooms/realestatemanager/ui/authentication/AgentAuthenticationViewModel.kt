package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.usecases.CreateAgentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgentAuthenticationViewModel @Inject constructor(private val createAgentUseCase: CreateAgentUseCase) : ViewModel() {

    suspend fun createAgent(agent : Agent) = createAgentUseCase.createAgent(agent)

    suspend fun createAgentInFirestoreDatabase(firebaseUser: FirebaseUser?) = createAgentUseCase.createAgentInFirestoreDatabase(firebaseUser)
}