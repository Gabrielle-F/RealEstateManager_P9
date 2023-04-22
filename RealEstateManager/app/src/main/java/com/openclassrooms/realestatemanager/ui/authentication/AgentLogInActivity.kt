package com.openclassrooms.realestatemanager.ui.authentication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.databinding.ActivityAgentConnectionBinding
import com.openclassrooms.realestatemanager.usecases.LogInAgentUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AgentLogInActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAgentConnectionBinding
    private val agentLogInActivityViewModel : AgentLogInActivityViewModel by viewModels()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAgentConnectionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureListeners()
    }

    private fun configureListeners() {
        val email = binding.editTxtEmailAgentConnection.text.toString()
        val password = binding.editTxtPasswordAgentConnection.text.toString()

        binding.logInBtnAgent.setOnClickListener {
            if(email != null && password != null) {
                logIn(email, password)
            } else {
                Toast.makeText(applicationContext, "Please enter your email and password !", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun logIn(email: String, password: String) {
        lifecycleScope.launch {
            agentLogInActivityViewModel.logIn(email, password)
        }
    }
}