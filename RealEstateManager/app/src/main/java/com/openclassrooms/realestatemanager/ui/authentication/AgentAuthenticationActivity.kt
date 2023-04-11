package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.databinding.ActivityAgentAuthenticationBinding
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AgentAuthenticationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAgentAuthenticationBinding
    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val agentAuthenticationViewModel : AgentAuthenticationViewModel by viewModels()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAgentAuthenticationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lifecycleScope.launch {
            configureListeners()
        }
    }

    private suspend fun configureListeners(id : Int = 0) {
        lateinit var email : String
        lateinit var name : String
        lateinit var password : String

        binding.signInBtnAgent.setOnClickListener {
            email = binding.editTxtEmailAgentAuthentication.text.toString()
            name = binding.editTxtNameAgentAuthentication.text.toString()
            password = binding.editTxtPasswordAgentAuthentication.text.toString()
            val agent = Agent(id, email, name)
            lifecycleScope.launch {
                createAgent(agent)
                createAgentAccount(email, password) }
            }
        binding.logInBtnAgent.setOnClickListener {
            email = binding.editTxtEmailAgentAuthentication.text.toString()
            password = binding.editTxtPasswordAgentAuthentication.text.toString()
            connectToAccount(email, password) }
    }

    private suspend fun createAgentAccount(email : String, password : String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    lifecycleScope.launch {
                        val firebaseUser : FirebaseUser? = firebaseAuth.currentUser
                        agentAuthenticationViewModel.createAgentInFirestoreDatabase(firebaseUser)
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Account creation : success !", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Account creation : failure ! Please retry later.", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun connectToAccount(email : String, password : String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Connection failed. Make sure your email and password are correct.", Toast.LENGTH_LONG).show()
                }
            }
    }

    private suspend fun createAgent(agent : Agent) {
        agentAuthenticationViewModel.createAgent(agent)
    }
}