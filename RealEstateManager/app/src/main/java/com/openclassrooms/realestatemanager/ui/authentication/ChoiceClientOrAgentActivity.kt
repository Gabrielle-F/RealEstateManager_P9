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
            val intent = Intent(this, ClientAuthenticationActivity::class.java)
            startActivity(intent)
        }
        binding.activityChoiceBtnAgent.setOnClickListener {
            val intent = Intent(this, AgentAuthenticationActivity::class.java)
            startActivity(intent)
        }
    }
}