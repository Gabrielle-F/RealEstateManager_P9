package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.usecases.CreateAgentUseCase
import com.openclassrooms.realestatemanager.usecases.CreateClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val createAgentUseCase: CreateAgentUseCase, private val createClientUseCase: CreateClientUseCase) : ViewModel() {

    val liveDataUserSignUp = MutableLiveData<Boolean>()
    suspend fun createAgent(agent : Agent) = createAgentUseCase.createAgent(agent)
    suspend fun createAgentWithEmailAndPassword(email: String, password: String, name: String) {
        val success = createAgentUseCase.createAgentWithEmailAndPassword(email, password, name)
        liveDataUserSignUp.postValue(success)
    }

    suspend fun createClient(client: Client) = createClientUseCase.createClient(client)

    suspend fun createClientWithEmailAndPassword(email: String, password: String, name: String) {
        val success = createClientUseCase.invoke(email, password, name)
        liveDataUserSignUp.postValue(success)
    }
}