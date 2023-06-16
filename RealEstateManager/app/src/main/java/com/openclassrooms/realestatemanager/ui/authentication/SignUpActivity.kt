package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.databinding.ActivitySignUpBinding
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    enum class CurrentUser {
        CLIENT, AGENT
    }

    private lateinit var binding : ActivitySignUpBinding
    private val viewModel : SignUpViewModel by viewModels()
    private lateinit var currentUser : CurrentUser

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureListeners()

        val clientBoolean = intent.getBooleanExtra("client", false)
        val agentBoolean = intent.getBooleanExtra("agent", false)
        if(clientBoolean) {
            currentUser = CurrentUser.CLIENT
        } else if(agentBoolean) {
            currentUser = CurrentUser.AGENT
        }
    }

    private fun configureListeners() {
        viewModel.liveDataUserSignUp.observe(this) { signUpSuccess ->
            if(signUpSuccess) {
                if(currentUser == CurrentUser.AGENT) {
                    val clientBoolean = false
                    val agentBoolean = true
                    startMainActivity(clientBoolean, agentBoolean)
                } else if(currentUser == CurrentUser.CLIENT) {
                    val clientBoolean = true
                    val agentBoolean = false
                    startMainActivity(clientBoolean, agentBoolean)
                }
            } else {
                Toast.makeText(this, "Sign up failed ! Please retry later", Toast.LENGTH_LONG).show()
            }
        }

        binding.signInBtn.setOnClickListener {
            val email = binding.editTxtEmailAgentAuthentication.text.toString()
            val name = binding.editTxtNameAgentAuthentication.text.toString()
            val password = binding.editTxtPasswordAgentAuthentication.text.toString()

            val id : String = "clientId"
            val client = Client(id, email, name)
            if(email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                if(currentUser == CurrentUser.CLIENT) {
                    createClientWithEmailAndPassword(email, password, name)
                    createClientInLocalDatabase(client)
                } else if (currentUser == CurrentUser.AGENT) {
                    createAgentWithEmailAndPassword(email, password)
                    val createdId = getCreatedId(name)
                    val agent = Agent(createdId, email, name)
                    createAgent(agent)
                }
            } else {
                Toast.makeText(applicationContext, "Please enter your informations !", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getCreatedId(name: String) : String {
        val firebaseUser = getCurrentFirebaseUser()
        val createdId = viewModel.createAgentInFirestoreDatabase(firebaseUser, name)
        return createdId
    }

    private fun createAgent(agent: Agent) {
        viewModel.createAgent(agent)
    }

    private fun createAgentWithEmailAndPassword(email: String, password: String) {
        viewModel.createAgentWithEmailAndPassword(email, password)
    }

    private fun createClientInLocalDatabase(client: Client) {
        lifecycleScope.launch {
            viewModel.createClient(client)
        }
    }

    private fun startMainActivity(client: Boolean, agent: Boolean) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("client", client)
        intent.putExtra("agent", agent)
        startActivity(intent)
    }

    private fun createClientWithEmailAndPassword(email: String, password: String, name: String) {
        lifecycleScope.launch {
            viewModel.createClientWithEmailAndPassword(email, password, name)
        }
    }

    private fun getCurrentFirebaseUser() : FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}