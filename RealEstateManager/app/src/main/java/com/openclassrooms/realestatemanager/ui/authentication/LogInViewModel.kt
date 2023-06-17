package com.openclassrooms.realestatemanager.ui.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.usecases.LogInAgentUseCase
import com.openclassrooms.realestatemanager.usecases.LogInClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInAgentUseCase: LogInAgentUseCase,
    private val logInClientUseCase: LogInClientUseCase
) : ViewModel() {

    val liveDataUserLogIn = MutableLiveData<Boolean>()

    fun logInAgent(email: String, password: String) {
        viewModelScope.launch {
            val success = logInAgentUseCase.invoke(email, password)
            liveDataUserLogIn.postValue(success)
        }
    }

    fun logInClient(email: String, password: String) {
        viewModelScope.launch {
            val success = logInClientUseCase.invoke(email, password)
            liveDataUserLogIn.postValue(success)
        }
    }
}