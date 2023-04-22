package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.usecases.CreateClientUseCase
import com.openclassrooms.realestatemanager.usecases.SignInClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class   ClientSignInActivityViewModel @Inject constructor(private val createClientUseCase: CreateClientUseCase, private val signInClientUseCase: SignInClientUseCase) : ViewModel() {

    suspend fun createClient(client : Client) = createClientUseCase.createClient(client)

    suspend fun createClientInFirestoreDatabase(firebaseUser: FirebaseUser?) = createClientUseCase.createClientInFirestoreDatabase(firebaseUser)

    suspend fun signIn(email: String, password: String) = signInClientUseCase.signIn(email, password)
}