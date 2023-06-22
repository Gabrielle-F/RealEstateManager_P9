package com.openclassrooms.realestatemanager.injection

import com.openclassrooms.realestatemanager.database.AgentDao
import com.openclassrooms.realestatemanager.database.ClientDao
import com.openclassrooms.realestatemanager.database.PropertyDao
import com.openclassrooms.realestatemanager.repository.AgentRepository
import com.openclassrooms.realestatemanager.repository.ClientRepository
import com.openclassrooms.realestatemanager.repository.PropertyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesPropertyRepository(propertyDao : PropertyDao) : PropertyRepository {
        return PropertyRepository(propertyDao)
    }

    @Provides
    fun provideAgentRepository(agentDao: AgentDao) : AgentRepository {
        return AgentRepository(agentDao)
    }

    @Provides
    fun provideClientRepository(clientDao: ClientDao) : ClientRepository {
        return ClientRepository(clientDao)
    }
}