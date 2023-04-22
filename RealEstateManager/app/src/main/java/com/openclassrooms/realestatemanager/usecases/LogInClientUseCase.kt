package com.openclassrooms.realestatemanager.usecases

import com.openclassrooms.realestatemanager.repository.ClientRepository
import javax.inject.Inject

class LogInClientUseCase @Inject constructor(private val clientRepository: ClientRepository) {

    suspend fun logIn(email: String, password: String) = clientRepository.logIn(email, password)
}