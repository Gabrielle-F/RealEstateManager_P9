package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.usecases.LogInAgentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgentLogInActivityViewModel @Inject constructor(private val logInAgentUseCase: LogInAgentUseCase) : ViewModel() {

    suspend fun logIn(email: String, password: String) = logInAgentUseCase.logIn(email, password)
}