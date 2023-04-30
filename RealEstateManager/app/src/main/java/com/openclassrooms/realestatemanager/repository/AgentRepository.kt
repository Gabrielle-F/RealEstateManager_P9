package com.openclassrooms.realestatemanager.repository

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.openclassrooms.realestatemanager.database.AgentDao
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.AgentFirestore
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.w3c.dom.Document
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentRepository @Inject constructor(private val agentDao : AgentDao) {

    private val agentsCollectionName : String = "agents"
    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val TAG_ERROR_SIGN_IN : String = "Error"
    private val TAG_ERROR_INCORRECT_PASSWORD_OR_EMAIL : String = "Wrong password or email"

    suspend fun createAgent(agent : Agent) = agentDao.insertAgent(agent)

    fun getAgentsCollection() : CollectionReference {
        return FirebaseFirestore.getInstance().collection(agentsCollectionName)
    }

    suspend fun logIn(email: String, password: String) : Boolean {
        val task : AuthResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return if (task.user != null) {
            getCurrentFirebaseUser()
            true
        } else {
            Log.e(TAG_ERROR_INCORRECT_PASSWORD_OR_EMAIL, "Wrong password or email")
            false
        }
    }

    suspend fun createUserWithEmailAndPassword(email: String, password: String, name: String) : Boolean {
        val task : AuthResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return if(task.user != null) {
            createAgentInFirestoreDatabase(task.user)
            true
        } else {
            Log.e(TAG_ERROR_SIGN_IN, "Error during authentication")
            false
        }
    }

    fun createAgentInFirestoreDatabase(firebaseUser : FirebaseUser?) {
        if (firebaseUser != null) {
            val id : String = firebaseUser.uid
            val email : String = firebaseUser.email.toString()
            val name : String = firebaseUser.displayName.toString()

            val userData : Task<DocumentSnapshot>? = getUserData()
            val agent = AgentFirestore(id, email, name)
            if (userData != null) {
                userData.addOnSuccessListener { getAgentsCollection().document(id).set(agent) }
            }
        }
    }

    fun getUserData() : Task<DocumentSnapshot>? {
        val uid : String? = this.getCurrentFirebaseUser()?.uid
        if (uid != null) {
            return this.getAgentsCollection().document(uid).get()
        } else {
            return null
        }
    }

    private fun getCurrentFirebaseUser() : FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun getAllAgents() : Flow<List<Agent>> = agentDao.getAllAgents()
}