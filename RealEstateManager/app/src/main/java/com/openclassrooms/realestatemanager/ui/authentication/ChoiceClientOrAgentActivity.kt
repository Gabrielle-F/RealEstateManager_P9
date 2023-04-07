package com.openclassrooms.realestatemanager.ui.authentication

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
            //TODO start ClientAuthenticationActivity
            // or launch pop-up for choice between
            // connection and create account
        }
        binding.activityChoiceBtnAgent.setOnClickListener {
            //TODO start AgentAuthenticationActivity
            // or launch pop-up for choice between
            // connection and create account
        }
    }
}