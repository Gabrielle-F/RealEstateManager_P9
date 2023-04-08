package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.realestatemanager.databinding.ActivityClientAuthenticationBinding
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ClientAuthenticationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityClientAuthenticationBinding
    private lateinit var firebaseAuth : FirebaseAuth

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientAuthenticationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()
        configureListeners()
    }

    private fun configureListeners() {
        val email : String = binding.editTxtEmailClientAuthentication.text.toString()
        val password : String = binding.editTxtPasswordAuthentication.text.toString()

        binding.signInBtnClient.setOnClickListener { createClientAccount(email, password) }
        binding.logInBtnClient.setOnClickListener { connectToAccount(email, password) }
    }


    private fun createClientAccount(email : String, password : String) {
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
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Connection failed. Make sure your email and password are correct.", Toast.LENGTH_LONG).show()
                }
            }
    }


}