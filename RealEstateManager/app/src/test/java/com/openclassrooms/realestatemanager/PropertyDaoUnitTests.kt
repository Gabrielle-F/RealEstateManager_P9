package com.openclassrooms.realestatemanager

import android.content.Context
import androidx.room.Room
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.model.LocalPicture
import com.openclassrooms.realestatemanager.model.Property
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class PropertyDaoUnitTests {

    val mockContext = mock(Context::class.java)
    private lateinit var database: RealEstateManagerDatabase
    private val dummyPicturesListFirstProperty = arrayListOf<LocalPicture>()
    private val dummyPicturesListSecondProperty = arrayListOf<LocalPicture>()
    private val firstLocalPictureFirstProperty =
        LocalPicture("content://path_1", "Pièce à vivre", "Pièce lumineuse et bien amenagée.")
    private val firstLocalPictureSecondProperty =
        LocalPicture("content://path_2", "Salon", "Salon spacieux et lumineux.")
    private val secondLocalPictureSecondProperty = LocalPicture(
        "content://path_3",
        "Cuisine",
        "Cuisine toute équipée, équipement neuf, idéale pour deux à trois personnes."
    )

    private val STUDIO_DUMMY_PROPERTY = Property(
        "hd7j3mp",
        "Studio",
        20000,
        "rue des roses",
        "2",
        20,
        1,
        "28270",
        "Paris",
        true,
        "02/06/2023",
        "07/06/2023",
        true,
        true,
        true,
        true,
        true,
        true,
        dummyPicturesListFirstProperty,
        1,
        "Studio idéal pour une personne, très bien desservi par les transports.",
        0.0,
        0.0,
        "Cheryl Blossom"
    )
    private val HOUSE_DUMMY_PROPERTY = Property(
        "kj5qpb3hbd",
        "House",
        105000,
        "rue des Erables",
        "10",
        120,
        7,
        "75002",
        "Paris",
        false,
        "29/05/2023",
        "",
        true,
        true,
        false,
        true,
        false,
        false,
        dummyPicturesListSecondProperty,
        5,
        "Maison lumineuse, récemment rénovée, idéale pour une famille de 5 personnes.",
        0.0,
        0.0,
        "Aron Blum"
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(mockContext, RealEstateManagerDatabase::class.java)
            .allowMainThreadQueries().build()
        dummyPicturesListFirstProperty.add(firstLocalPictureFirstProperty)
        dummyPicturesListSecondProperty.add(firstLocalPictureSecondProperty)
        dummyPicturesListSecondProperty.add(secondLocalPictureSecondProperty)
    }

    @After
    fun closeDatabase() = database.close()

    @Test
    @Throws(InterruptedException::class)
    fun insertPropertyAndGetPropertyById() = runBlocking {
        database.propertyDao().insertProperty(STUDIO_DUMMY_PROPERTY)
        val propertyRetrieved =
            database.propertyDao().getPropertyById(STUDIO_DUMMY_PROPERTY.id)?.first()
        assertEquals(STUDIO_DUMMY_PROPERTY.id, propertyRetrieved?.id)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertPropertiesAndRetrieveAllProperties() = runBlocking {
        database.propertyDao().insertProperty(STUDIO_DUMMY_PROPERTY)
        database.propertyDao().insertProperty(HOUSE_DUMMY_PROPERTY)
        val propertiesRetrieved = database.propertyDao().getAllProperties().first()
        assertEquals(2, propertiesRetrieved.size)
    }

}