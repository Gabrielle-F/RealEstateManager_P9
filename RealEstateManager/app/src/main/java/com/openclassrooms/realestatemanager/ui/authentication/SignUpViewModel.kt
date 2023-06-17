package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.usecases.CreateAgentUseCase
import com.openclassrooms.realestatemanager.usecases.CreateClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createAgentUseCase: CreateAgentUseCase,
    private val createClientUseCase: CreateClientUseCase
) : ViewModel() {

    val liveDataUserSignUp = MutableLiveData<Boolean>()

    fun createAgent(agent: Agent) {
        viewModelScope.launch {
            createAgentUseCase.createAgent(agent)
        }
    }

    fun createAgentWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val success = createAgentUseCase.createAgentWithEmailAndPassword(email, password)
            liveDataUserSignUp.postValue(success)
        }
    }

    fun createAgentInFirestoreDatabase(firebaseUser: FirebaseUser?, name: String): String =
        createAgentUseCase.createAgentInFirestoreDatabase(firebaseUser, name)

    fun createClient(client: Client) {
        viewModelScope.launch {
            createClientUseCase.createClient(client)
        }
    }

    fun createClientWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val success = createClientUseCase.invoke(email, password)
            liveDataUserSignUp.postValue(success)
        }
    }

    fun createClientInFirestoreDatabase(firebaseUser: FirebaseUser?, name: String): String =
        createClientUseCase.createClientInFirestoreDatabase(firebaseUser, name)
}