package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.databinding.ActivityAgentConnectionBinding
import com.openclassrooms.realestatemanager.ui.main.MainActivity
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
        binding.logInBtnAgent.setOnClickListener {
            val email = binding.editTxtEmailAgentConnection.text.toString()
            val password = binding.editTxtPasswordAgentConnection.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()) {
                logIn(email, password)
                startMainActivity()
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

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}