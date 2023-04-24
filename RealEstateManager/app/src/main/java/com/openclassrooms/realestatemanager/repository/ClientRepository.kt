package com.openclassrooms.realestatemanager.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.openclassrooms.realestatemanager.database.ClientDao
import com.openclassrooms.realestatemanager.model.Client
import com.openclassrooms.realestatemanager.model.ClientFirestore
import javax.inject.Inject

class ClientRepository @Inject constructor(private val clientDao : ClientDao) {

    private val clientCollectionName : String = "clients"
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val TAG_ERROR_SIGN_IN : String = "Error"
    private val TAG_ERROR_INCORRECT_PASSWORD_OR_EMAIL : String = "Wrong password or email"

    suspend fun createClient(client : Client) = clientDao.createClient(client)

    fun getClientsCollection() : CollectionReference {
        return FirebaseFirestore.getInstance().collection(clientCollectionName)
    }

    suspend fun logIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if(task.isSuccessful) {
                getCurrentFirebaseUser()
            } else {
                val exception = task.exception
                if(exception is FirebaseAuthInvalidCredentialsException) {
                    Log.e(TAG_ERROR_INCORRECT_PASSWORD_OR_EMAIL, "Wrong password or email")
                }
            }
        }
    }

    suspend fun createUserWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if(task.isSuccessful) {
                val firebaseUser = firebaseAuth.currentUser
                createClientInFirestoreDatabase(firebaseUser)
            } else {
                val exception = task.exception
                Log.e(TAG_ERROR_SIGN_IN, "Error during authentication", exception)
            }
        }
    }

    fun createClientInFirestoreDatabase(firebaseUser: FirebaseUser?) {
        if (firebaseUser != null) {
            val id : String = firebaseUser.uid
            val email : String = firebaseUser.email.toString()

            val userData : Task<DocumentSnapshot>? = getUserData()
            val client = ClientFirestore(id, email)
            if (userData != null) {
                userData.addOnCompleteListener { getClientsCollection().document(id).set(client) }
            }
        }
    }

    fun getUserData() : Task<DocumentSnapshot>? {
        val uid : String? = this.getCurrentFirebaseUser()?.uid
        if (uid != null) {
            return this.getClientsCollection().document(uid).get()
        } else {
            return null
        }
    }

    private fun getCurrentFirebaseUser() : FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }


}