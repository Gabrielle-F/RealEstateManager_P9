package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.realestatemanager.databinding.ActivityClientConnectionBinding
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ClientLogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientConnectionBinding
    private val clientLogInActivityViewModel: ClientLogInActivityViewModel by viewModels()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientConnectionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureListeners()
    }

    private fun configureListeners() {
        binding.logInBtnClient.setOnClickListener {
            val email : String = binding.editTxtEmailClientAuthentication.text.toString()
            val password : String = binding.editTxtPasswordAuthentication.text.toString()
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
            clientLogInActivityViewModel.logIn(email, password)
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}