package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.usecases.CreateClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClientAuthenticationViewModel @Inject constructor(private val createClientUseCase: CreateClientUseCase) : ViewModel() {

    suspend fun createClient(client : Client) = createClientUseCase.createClient(client)

    suspend fun createClientInFirestoreDatabase(firebaseUser: FirebaseUser?) = createClientUseCase.createClientInFirestoreDatabase(firebaseUser)
}