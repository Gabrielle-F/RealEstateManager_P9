package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.usecases.LogInAgentUseCase
import com.openclassrooms.realestatemanager.usecases.LogInClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val logInAgentUseCase: LogInAgentUseCase, private val logInClientUseCase: LogInClientUseCase) : ViewModel() {

    val liveDataUserLogIn = MutableLiveData<Boolean>()
    suspend fun logIn(email: String, password: String) {
        val success = logInAgentUseCase.invoke(email, password)
        liveDataUserLogIn.postValue(success)
    }

    suspend fun logInClient(email: String, password: String) {
        val success = logInClientUseCase.invoke(email, password)
        liveDataUserLogIn.postValue(success)
    }
}