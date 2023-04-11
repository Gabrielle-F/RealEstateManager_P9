package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.databinding.ActivityClientAuthenticationBinding
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ClientAuthenticationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityClientAuthenticationBinding
    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val clientAuthenticationViewModel : ClientAuthenticationViewModel by viewModels()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientAuthenticationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureListeners()
    }

    private fun configureListeners(id : Int = 0) {
        lateinit var email : String
        lateinit var password : String

        binding.signInBtnClient.setOnClickListener {
            lifecycleScope.launch {
                email = binding.editTxtEmailClientAuthentication.text.toString()
                password = binding.editTxtPasswordAuthentication.text.toString()
                val client = Client(id, email)
                clientAuthenticationViewModel.createClient(client)
            }
            createClientAccount(email, password) }

        binding.logInBtnClient.setOnClickListener { connectToAccount(email, password) }

    }


    private fun createClientAccount(email : String, password : String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    lifecycleScope.launch {
                        val firebaseUser : FirebaseUser? = firebaseAuth.currentUser
                        if (firebaseUser != null) {
                            clientAuthenticationViewModel.createClientInFirestoreDatabase(firebaseUser)
                        }
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


}