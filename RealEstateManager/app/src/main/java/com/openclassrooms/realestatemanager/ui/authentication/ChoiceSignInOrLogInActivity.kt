package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.databinding.ActivityChoiceSigninOrLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoiceSignInOrLogInActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChoiceSigninOrLoginBinding

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChoiceSigninOrLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureListeners()
    }

    private fun configureListeners() {
        val choiceClient = intent.getBooleanExtra("clientChoice", false)
        val choiceAgent = intent.getBooleanExtra("agentChoice", false)
        binding.activityChoiceBtnSignIn.setOnClickListener {
            if(choiceClient) {
                val intent = Intent(this, ClientSignInActivity::class.java)
                startActivity(intent)
            } else if (choiceAgent) {
                val intent = Intent(this, AgentSignInActivity::class.java)
                startActivity(intent)
            }
        }
        binding.activityChoiceBtnLogIn.setOnClickListener {
            if(choiceClient) {
                val intent = Intent(this, ClientLogInActivity::class.java)
                startActivity(intent)
            } else if (choiceAgent) {
                val intent = Intent(this, AgentLogInActivity::class.java)
                startActivity(intent)
            }
        }
    }
}