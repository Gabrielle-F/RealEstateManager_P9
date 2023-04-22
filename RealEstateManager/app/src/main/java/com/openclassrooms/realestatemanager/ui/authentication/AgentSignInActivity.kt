package com.openclassrooms.realestatemanager.ui.authentication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.databinding.ActivityAgentCreateAccountBinding
import com.openclassrooms.realestatemanager.model.Agent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AgentSignInActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAgentCreateAccountBinding
    private val agentSignInActivityViewModel : AgentSignInActivityViewModel by viewModels()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAgentCreateAccountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureListeners()
    }

    private fun configureListeners(id : Int = 0) {
        val email = binding.editTxtEmailAgentAuthentication.text.toString()
        val name = binding.editTxtNameAgentAuthentication.text.toString()
        val password = binding.editTxtPasswordAgentAuthentication.text.toString()

        val agent = Agent(id, email, name)

        binding.signInBtnAgent.setOnClickListener {
            if(email != null && password != null) {
                signIn(email, password)
                if(name != null) {
                    createAgentInLocalDatabase(agent)
                }
            } else {
                Toast.makeText(applicationContext, "Please enter your email and password !", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signIn(email: String, password: String) {
        lifecycleScope.launch {
            agentSignInActivityViewModel.signIn(email, password)
        }
    }

    private fun createAgentInLocalDatabase(agent: Agent) {
        lifecycleScope.launch {
            agentSignInActivityViewModel.createAgent(agent)
        }
    }
}