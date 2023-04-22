package com.openclassrooms.realestatemanager.ui.authentication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.realestatemanager.databinding.ActivityClientConnectionBinding
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
        lateinit var email: String
        lateinit var password: String

        binding.logInBtnClient.setOnClickListener {
            if(email != null && password != null) {
                logIn(email, password)
            }
        }
    }

    private fun logIn(email: String, password: String) {
        lifecycleScope.launch {
            clientLogInActivityViewModel.logIn(email, password)
        }
    }
}