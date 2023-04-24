package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.databinding.ActivityClientCreateAccountBinding
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.ui.main.MainActivity
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
        binding.signInBtnClient.setOnClickListener {
            val email = binding.editTxtEmailClientCreateAccount.text.toString()
            val password = binding.editTxtPasswordCreateAccount.text.toString()

            val client = Client(id, email)
            if(email.isNotEmpty() && password.isNotEmpty()) {
                createClientWithEmailAndPassword(email, password)
                createClientInLocalDatabase(client)
                startMainActivity()
            } else {
                Toast.makeText(applicationContext, "Please enter your email and password !", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createClientInLocalDatabase(client: Client) {
        lifecycleScope.launch {
            clientSignInActivityViewModel.createClient(client)
        }
    }

    private fun createClientWithEmailAndPassword(email: String, password: String) {
        lifecycleScope.launch{
            clientSignInActivityViewModel.createClientWithEmailAndPassword(email, password)
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}