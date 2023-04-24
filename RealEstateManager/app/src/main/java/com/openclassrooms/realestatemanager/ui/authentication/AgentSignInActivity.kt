package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.databinding.ActivityAgentCreateAccountBinding
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.ui.main.MainActivity
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
        binding.signInBtnAgent.setOnClickListener {
            val email = binding.editTxtEmailAgentAuthentication.text.toString()
            val name = binding.editTxtNameAgentAuthentication.text.toString()
            val password = binding.editTxtPasswordAgentAuthentication.text.toString()

            val agent = Agent(id, email, name)
            if(email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                createAgentWithEmailAndPassword(email, password, name)
                createAgentInLocalDatabase(agent)
                startMainActivity()
            } else {
                Toast.makeText(applicationContext, "Please enter your name !", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createAgentInLocalDatabase(agent: Agent) {
        lifecycleScope.launch {
            agentSignInActivityViewModel.createAgent(agent)
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun createAgentWithEmailAndPassword(email: String, password: String, name: String) {
        lifecycleScope.launch {
            agentSignInActivityViewModel.createAgentWithEmailAndPassword(email, password, name)
        }
    }
}