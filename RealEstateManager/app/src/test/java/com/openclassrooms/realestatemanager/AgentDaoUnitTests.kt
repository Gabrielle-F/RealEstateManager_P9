package com.openclassrooms.realestatemanager

import android.content.Context
import androidx.room.Room
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.model.Agent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class AgentDaoUnitTests {

    val mockContext = Mockito.mock(Context::class.java)
    private lateinit var database : RealEstateManagerDatabase

    private val FIRST_DUMMY_AGENT = Agent(0, "firstDummyAgent@email.com", "First Agent")
    private val SECOND_DUMMY_AGENT = Agent(1, "secondDummyAgent@email.com", "Second Agent")
    private val THIRD_DUMMY_AGENT = Agent(2, "thirdDummyAgent@email.com", "Third Agent")

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(mockContext, RealEstateManagerDatabase::class.java)
            .allowMainThreadQueries().build()
    }

    @After
    fun closeDatabase() = database.close()

    @Test
    fun insertAgentAndGetAgentById() = runBlocking {
        database.agentDao().insertAgent(FIRST_DUMMY_AGENT)
        val agentRetrieved = database.agentDao().getAgentById(FIRST_DUMMY_AGENT.id).first()
        assertEquals(FIRST_DUMMY_AGENT.id, agentRetrieved.id)
    }

    @Test
    fun insertAgentsAndRetrieveAllAgents() = runBlocking {
        database.agentDao().insertAgent(FIRST_DUMMY_AGENT)
        database.agentDao().insertAgent(SECOND_DUMMY_AGENT)
        database.agentDao().insertAgent(THIRD_DUMMY_AGENT)
        val agentsRetrieved = database.agentDao().getAllAgents().first()
        assertEquals(3, agentsRetrieved.size)
    }
}