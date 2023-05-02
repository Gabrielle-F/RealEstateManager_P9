package com.openclassrooms.realestatemanager.usecases

import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.repository.ClientRepository
import javax.inject.Inject

class CreateClientUseCase @Inject constructor(private val clientRepository : ClientRepository) {

    suspend fun createClient(client : Client) = clientRepository.createClient(client)

    suspend fun invoke(email: String, password: String, name: String) : Boolean = clientRepository.createUserWithEmailAndPassword(email, password, name)
}