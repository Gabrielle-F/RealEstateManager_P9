package com.openclassrooms.realestatemanager.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.openclassrooms.realestatemanager.database.AgentDao
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.AgentFirestore
import org.w3c.dom.Document
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentRepository @Inject constructor(private val agentDao : AgentDao) {

    private val agentsCollectionName : String = "agents"

    suspend fun createAgent(agent : Agent) = agentDao.insertAgent(agent)

    fun getAgentsCollection() : CollectionReference {
        return FirebaseFirestore.getInstance().collection(agentsCollectionName)
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
}