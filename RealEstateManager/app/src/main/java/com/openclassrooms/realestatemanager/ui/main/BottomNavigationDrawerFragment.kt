package com.openclassrooms.realestatemanager.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentBottomsheetBinding
import com.openclassrooms.realestatemanager.ui.authentication.ChoiceClientOrAgentActivity

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentBottomsheetBinding

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBottomsheetBinding.inflate(layoutInflater)
        return binding.root
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.navigation_view_menu, menu)
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            R.id.logout ->
                signOut(context)
        }
        return true
    }

    private fun signOut(context: Context?) {
        if(context != null) {
            AuthUI.getInstance().signOut(context).addOnCompleteListener {
                    task -> Toast.makeText(getContext(), "Logout successful !", Toast.LENGTH_LONG).show()
            }
        }
    }
}