package com.openclassrooms.realestatemanager

import android.app.Application
import com.openclassrooms.realestatemanager.utils.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DaggerHiltApplication : Application() {

    lateinit var appPreferences: AppPreferences

    override fun onCreate() {
        super.onCreate()
        appPreferences = AppPreferences(applicationContext)
    }
}