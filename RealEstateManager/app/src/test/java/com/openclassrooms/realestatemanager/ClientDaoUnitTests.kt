package com.openclassrooms.realestatemanager

import android.content.Context
import androidx.room.Room
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.model.Client
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ClientDaoUnitTests {

    val mockContext = Mockito.mock(Context::class.java)
    private lateinit var database: RealEstateManagerDatabase

    private val FIRST_DUMMY_CLIENT = Client("0", "firstClient@email.com", "First Client")
    private val SECOND_DUMMY_CLIENT = Client("1", "secondClient@email.com", "Second Client")
    private val THIRD_DUMMY_CLIENT = Client("2", "thirdClient@email.com", "Third Client")

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(mockContext, RealEstateManagerDatabase::class.java)
            .allowMainThreadQueries().build()
    }

    @After
    fun closeDatabase() = database.close()

    @Test
    fun insertClientAndGetClientById() = runBlocking {
        database.clientDao().createClient(FIRST_DUMMY_CLIENT)
        val retrievedClient = database.clientDao().getClientById(FIRST_DUMMY_CLIENT.id).first()
        assertEquals(FIRST_DUMMY_CLIENT.id, retrievedClient.id)
    }

    @Test
    fun insertClientsAndRetrieveAllClients() = runBlocking {
        database.clientDao().createClient(FIRST_DUMMY_CLIENT)
        database.clientDao().createClient(SECOND_DUMMY_CLIENT)
        database.clientDao().createClient(THIRD_DUMMY_CLIENT)
        val retrievedClients = database.clientDao().getAllClients().first()
        assertEquals(3, retrievedClients.size)
    }
}