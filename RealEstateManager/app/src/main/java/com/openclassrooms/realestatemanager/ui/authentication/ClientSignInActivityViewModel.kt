package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.usecases.CreateClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClientSignInActivityViewModel @Inject constructor(private val createClientUseCase: CreateClientUseCase) : ViewModel() {

    suspend fun createClient(client : Client) = createClientUseCase.createClient(client)

    suspend fun createClientWithEmailAndPassword(email: String, password: String) = createClientUseCase.invoke(email, password)
}