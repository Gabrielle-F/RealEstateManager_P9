package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.repository.ClientRepository
import javax.inject.Inject

class LogInClientUseCase @Inject constructor(private val clientRepository: ClientRepository) {

    suspend fun invoke(email: String, password: String) : Boolean {
        return clientRepository.logIn(email, password)
    }
}