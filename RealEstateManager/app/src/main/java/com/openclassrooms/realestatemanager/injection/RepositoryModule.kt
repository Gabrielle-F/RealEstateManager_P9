package com.openclassrooms.realestatemanager.injection

import com.openclassrooms.realestatemanager.database.PropertyDao
import com.openclassrooms.realestatemanager.repository.PropertyRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    fun providesPropertyRepository(propertyDao : PropertyDao) : PropertyRepository {
        return PropertyRepository(propertyDao)
    }
}