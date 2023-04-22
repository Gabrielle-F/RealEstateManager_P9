package com.openclassrooms.realestatemanager.ui.authentication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.databinding.ActivityClientCreateAccountBinding
import com.openclassrooms.realestatemanager.model.Client
import kotlinx.coroutines.launch

class ClientSignInActivity : AppCompatActivity() {

    private lateinit var binding : ActivityClientCreateAccountBinding
    private val clientSignInActivityViewModel : ClientSignInActivityViewModel by viewModels()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientCreateAccountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureListeners()
    }

    private fun configureListeners(id : Int = 0) {
        val email = binding.editTxtEmailClientCreateAccount.text.toString()
        val password = binding.editTxtPasswordCreateAccount.text.toString()

        val client = Client(id, email)
        binding.signInBtnClient.setOnClickListener {
            if(email != null && password != null) {
                signIn(email, password)
                createClientInLocalDatabase(client)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        lifecycleScope.launch {
            clientSignInActivityViewModel.signIn(email, password)
        }
    }

    private fun createClientInLocalDatabase(client: Client) {
        lifecycleScope.launch {
            clientSignInActivityViewModel.createClient(client)
        }
    }
}