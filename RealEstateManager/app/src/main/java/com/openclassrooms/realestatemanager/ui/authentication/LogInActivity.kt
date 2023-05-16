package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityLoginBinding
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val viewModel : LogInViewModel by viewModels()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureListeners()
    }

    private fun configureListeners() {
        var client = false
        var agent = false
        viewModel.liveDataUserLogIn.observe(this) { logInSuccess ->
            if(logInSuccess) {
                startMainActivity(client, agent)
            } else {
                Toast.makeText(this, "Log In failed ! Please retry later", Toast.LENGTH_LONG).show()
            }
        }

        binding.loginActivityRadioGroup.setOnCheckedChangeListener{ group, checkedId ->
            if(R.id.agent_radio_btn == checkedId) {
                agent = true
                client = false
            } else if (R.id.client_radio_btn == checkedId) {
                agent = false
                client = true
            }
        }

        binding.logInBtn.setOnClickListener {
            val email = binding.editTxtEmailAgentConnection.text.toString()
            val password = binding.editTxtPasswordAgentConnection.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()) {
                if(agent) {
                    logInAgent(email, password)
                } else if(client) {
                    logInClient(email, password)
                }
            } else {
                Toast.makeText(applicationContext, "Please enter your email and password !", Toast.LENGTH_LONG).show()
            }
        }

        binding.activityLoginSignUpBtn.setOnClickListener {
            if(!client && !agent) {
                Toast.makeText(this, "Please tell us if you are client or agent !", Toast.LENGTH_LONG).show()
            } else {
                startSignUpActivity(client, agent)
            }
        }
    }

    private fun logInAgent(email: String, password: String) {
        lifecycleScope.launch {
            viewModel.logIn(email, password)
        }
    }

    private fun logInClient(email: String, password: String) {
        lifecycleScope.launch {
            viewModel.logInClient(email, password)
        }
    }

    private fun startMainActivity(client: Boolean, agent: Boolean) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("client", client)
        intent.putExtra("agent", agent)
        startActivity(intent)
    }

    private fun startSignUpActivity(client: Boolean, agent: Boolean) {
        val intent = Intent(this, SignUpActivity::class.java)
        intent.putExtra("client", client)
        intent.putExtra("agent", agent)
        startActivity(intent)
    }
}