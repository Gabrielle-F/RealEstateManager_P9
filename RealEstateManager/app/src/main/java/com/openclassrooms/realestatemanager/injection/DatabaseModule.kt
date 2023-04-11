package com.openclassrooms.realestatemanager.injection

import android.content.Context
import androidx.room.Room
import com.openclassrooms.realestatemanager.database.AgentDao
import com.openclassrooms.realestatemanager.database.ClientDao
import com.openclassrooms.realestatemanager.database.PropertyDao
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext : Context) : RealEstateManagerDatabase {
        return Room.databaseBuilder(
            appContext,
            RealEstateManagerDatabase::class.java,
            "RealEstateManagerDatabase"
        ).build()
    }

    @Provides
    fun providePropertyDao(realEstateManagerDatabase: RealEstateManagerDatabase) : PropertyDao {
        return realEstateManagerDatabase.propertyDao()
    }

    @Provides
    fun provideAgentDao(realEstateManagerDatabase: RealEstateManagerDatabase) : AgentDao {
        return realEstateManagerDatabase.agentDao()
    }

    @Provides
    fun provideClientDao(realEstateManagerDatabase: RealEstateManagerDatabase) : ClientDao {
        return realEstateManagerDatabase.clientDao()
    }
}