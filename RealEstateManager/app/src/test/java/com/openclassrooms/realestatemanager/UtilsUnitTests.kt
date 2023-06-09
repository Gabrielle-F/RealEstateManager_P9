package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class UtilsUnitTests {

    @Test
    fun testConvertDollarToEuro() {
        val dollars = 100
        val expectedEuros = 81
        val actualEuros = Utils.convertDollarToEuro(dollars)
        assertEquals(expectedEuros, actualEuros)
    }

    @Test
    fun testConvertEuroToDollar() {
        val euros = 100
        val expectedDollars = 123
        val actualDollars = Utils.convertEuroToDollar(euros)
        assertEquals(expectedDollars, actualDollars)
    }

    @Test
    fun testGetTodayDate() {
        val expectedDateFormat = "dd/MM/yyyy"
        val expectedDate = SimpleDateFormat(expectedDateFormat).format(Date())
        val actualDate = Utils.getTodayDate()
        assertEquals(expectedDate, actualDate)
    }
}