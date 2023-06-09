package com.openclassrooms.realestatemanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.test.core.app.ApplicationProvider
import com.openclassrooms.realestatemanager.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ConnectivityInstrumentedTests {

    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkInfo: NetworkInfo

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        connectivityManager = mock(ConnectivityManager::class.java)
        networkInfo = mock(NetworkInfo::class.java)
    }

    @Test
    fun isInternetAvailable_withActiveNetwork_shouldReturnTrue() {
        `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        `when`(networkInfo.isAvailable).thenReturn(true)

        val result = Utils.isInternetAvailable(context)

        assertEquals(true, result)
    }

    @Test
    fun isInternetAvailable_withNullActiveNetwork_shouldReturnFalse() {
        `when`(connectivityManager.activeNetworkInfo).thenReturn(null)

        val result = Utils.isInternetAvailable(context)

        assertEquals(false, result)
    }

    @Test
    fun isInternetAvailable_withInactiveNetwork_shouldReturnFalse() {
        `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        `when`(networkInfo.isAvailable).thenReturn(false)

        val result = Utils.isInternetAvailable(context)

        assertEquals(false, result)
    }
}