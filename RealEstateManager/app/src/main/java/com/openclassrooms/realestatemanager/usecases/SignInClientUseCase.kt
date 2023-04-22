package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.repository.ClientRepository
import javax.inject.Inject

class SignInClientUseCase @Inject constructor(private val clientRepository: ClientRepository) {

    suspend fun signIn(email: String, password: String) = clientRepository.signIn(email, password)
}