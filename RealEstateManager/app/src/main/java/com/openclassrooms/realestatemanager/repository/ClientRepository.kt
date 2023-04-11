package com.openclassrooms.realestatemanager.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
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

    suspend fun createClient(client : Client) = clientDao.createClient(client)

    fun getClientsCollection() : CollectionReference {
        return FirebaseFirestore.getInstance().collection(clientCollectionName)
    }

    suspend fun createClientInFirestoreDatabase(firebaseUser: FirebaseUser?) {
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