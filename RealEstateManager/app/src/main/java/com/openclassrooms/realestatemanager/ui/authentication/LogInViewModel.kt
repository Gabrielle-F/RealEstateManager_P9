package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.usecases.LogInAgentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val logInAgentUseCase: LogInAgentUseCase) : ViewModel() {

    val liveDataUserLogIn = MutableLiveData<Boolean>()
    suspend fun logIn(email: String, password: String) {
        val success = logInAgentUseCase.invoke(email, password)
        liveDataUserLogIn.postValue(success)
    }
}