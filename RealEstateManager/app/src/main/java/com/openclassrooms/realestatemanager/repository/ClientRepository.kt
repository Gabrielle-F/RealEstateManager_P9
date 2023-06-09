package com.openclassrooms.realestatemanager.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.openclassrooms.realestatemanager.database.ClientDao
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.model.ClientFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClientRepository @Inject constructor(private val clientDao: ClientDao) {

    companion object {
        private const val TAG_ERROR_SIGN_IN = "Error"
        private const val TAG_ERROR_INCORRECT_PASSWORD_OR_EMAIL = "Wrong password or email"
    }

    private val clientCollectionName: String = "clients"
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun createClient(client: Client) = clientDao.createClient(client)

    private fun getClientsCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(clientCollectionName)
    }

    suspend fun logIn(email: String, password: String): Boolean {
        val task = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return if (task.user != null) {
            getCurrentFirebaseUser()
            true
        } else {
            Log.e(TAG_ERROR_INCORRECT_PASSWORD_OR_EMAIL, "Wrong password or email")
            false
        }
    }

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Boolean {
        val task = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return if (task.user != null) {
            true
        } else {
            Log.e(TAG_ERROR_SIGN_IN, "Error during authentication")
            false
        }
    }

    fun createClientInFirestoreDatabase(firebaseUser: FirebaseUser?, name: String): String {
        val newClientRef = getClientsCollection().document()
        var createdId = "clientId"
        if (firebaseUser != null) {
            val email: String = firebaseUser.email.toString()

            val userData: Task<DocumentSnapshot>? = getUserData()
            val client = ClientFirestore(email, name)
            userData?.let { data ->
                data.addOnSuccessListener {
                    newClientRef.set(client).addOnSuccessListener {
                        createdId = newClientRef.id
                    }
                }
            }
        }
        return createdId
    }

    private fun getUserData(): Task<DocumentSnapshot>? {
        val uid: String? = this.getCurrentFirebaseUser()?.uid
        return if (uid != null) {
            this.getClientsCollection().document(uid).get()
        } else {
            null
        }
    }

    private fun getCurrentFirebaseUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}