package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.realestatemanager.databinding.ActivityAgentAuthenticationBinding
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AgentAuthenticationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAgentAuthenticationBinding
    private lateinit var firebaseAuth : FirebaseAuth

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAgentAuthenticationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()
        configureListeners()
    }

    private fun configureListeners() {
        val email : String = binding.editTxtEmailAgentAuthentication.text.toString()
        val password : String = binding.editTxtPasswordAgentAuthentication.text.toString()

        binding.signInBtnAgent.setOnClickListener { createAgentAccount(email, password) }
        binding.logInBtnAgent.setOnClickListener { connectToAccount(email, password) }
    }

    private fun createAgentAccount(email : String, password : String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
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
                }
            }
    }
}