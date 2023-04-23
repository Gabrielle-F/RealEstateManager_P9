package com.openclassrooms.realestatemanager.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.databinding.ActivityChoiceClientOrAgentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoiceClientOrAgentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChoiceClientOrAgentBinding

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChoiceClientOrAgentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureListeners()
    }

    fun configureListeners() {
        binding.activityChoiceBtnClient.setOnClickListener {
            val booleanChoiceClient = true
            val booleanAgent = false
            val intent = Intent(this, ChoiceSignInOrLogInActivity::class.java)
            intent.putExtra("clientChoice", booleanChoiceClient)
            intent.putExtra("agentChoice", booleanAgent)
            startActivity(intent)
        }
        binding.activityChoiceBtnAgent.setOnClickListener {
            val booleanAgentClient = true
            val booleanClient = false
            val intent = Intent(this, ChoiceSignInOrLogInActivity::class.java)
            intent.putExtra("agentChoice", booleanAgentClient)
            intent.putExtra("clientChoice", booleanClient)
            startActivity(intent)
        }
    }
}