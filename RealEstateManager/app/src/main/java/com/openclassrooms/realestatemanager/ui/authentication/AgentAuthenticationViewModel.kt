package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.usecases.CreateAgentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgentAuthenticationViewModel @Inject constructor(private val createAgentUseCase: CreateAgentUseCase) : ViewModel() {

    fun createAgent(agent : Agent) = createAgentUseCase.createAgent(agent)
}