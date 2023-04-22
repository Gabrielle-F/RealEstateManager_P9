package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.usecases.LogInClientUseCase
import javax.inject.Inject

class ClientLogInActivityViewModel @Inject constructor(private val logInClientUseCase: LogInClientUseCase) : ViewModel() {

    suspend fun logIn(email: String, password: String) = logInClientUseCase.logIn(email, password)


}