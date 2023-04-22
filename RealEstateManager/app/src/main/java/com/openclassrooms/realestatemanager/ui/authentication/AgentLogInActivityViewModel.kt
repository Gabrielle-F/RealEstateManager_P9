package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.usecases.LogInAgentUseCase
import javax.inject.Inject

class AgentLogInActivityViewModel @Inject constructor(private val logInAgentUseCase: LogInAgentUseCase) : ViewModel() {

    suspend fun logIn(email: String, password: String) = logInAgentUseCase.logIn(email, password)
}