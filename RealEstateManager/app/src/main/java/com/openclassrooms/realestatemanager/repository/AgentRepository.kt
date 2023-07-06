package com.openclassrooms.realestatemanager.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.openclassrooms.realestatemanager.database.AgentDao
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.AgentFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentRepository @Inject constructor(private val agentDao: AgentDao) {

    companion object {
        private const val TAG_ERROR_SIGN_IN = "Sign In Error"
        private const val TAG_ERROR_INCORRECT_PASSWORD_OR_EMAIL = "Wrong password or mail"
    }

    private val agentsCollectionName: String = "agents"
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun createAgent(agent: Agent) = agentDao.insertAgent(agent)

    private fun getAgentsCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(agentsCollectionName)
    }

    suspend fun logIn(email: String, password: String): Boolean {
        val task: AuthResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return if (task.user != null) {
            getCurrentFirebaseUser()
            true
        } else {
            Log.e(TAG_ERROR_INCORRECT_PASSWORD_OR_EMAIL, "Wrong password or email")
            false
        }
    }

    suspend fun createUserWithEmailAndPassword(email: String, password: String): Boolean {
        val task: AuthResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return if (task.user != null) {
            true
        } else {
            Log.e(TAG_ERROR_SIGN_IN, "Error during authentication")
            false
        }
    }

    fun createAgentInFirestoreDatabase(firebaseUser: FirebaseUser?, name: String): String {
        val newAgentRef = getAgentsCollection().document()
        var createdId = "agentId"
        if (firebaseUser != null) {
            val email: String = firebaseUser.email.toString()

            val userData: Task<DocumentSnapshot>? = getUserData()
            val agent = AgentFirestore(email, name)
            userData?.let { data ->
                data.addOnSuccessListener {
                    newAgentRef.set(agent).addOnSuccessListener {
                        createdId = newAgentRef.id
                    }
                }
            }
        }
        return createdId
    }

    private fun getUserData(): Task<DocumentSnapshot>? {
        val uid: String? = this.getCurrentFirebaseUser()?.uid
        return if (uid != null) {
            this.getAgentsCollection().document(uid).get()
        } else {
            null
        }
    }

    private fun getCurrentFirebaseUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun getAllAgents(): Flow<List<Agent>> = agentDao.getAllAgents()

    fun getAgentById(id: String): Flow<Agent> = agentDao.getAgentById(id)

    fun getAgentsList(): Flow<List<Agent>> = flow {
        getAgentsCollection().get().await().map {
            it.toObject(Agent::class.java)
        }.let { agentsList ->
            emit(agentsList)
        }
    }
}