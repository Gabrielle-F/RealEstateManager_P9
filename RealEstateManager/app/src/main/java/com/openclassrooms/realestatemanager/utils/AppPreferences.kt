package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences? = context.getSharedPreferences("CurrencyExchange", Context.MODE_PRIVATE)

    fun getSelectedCurrency() : String {
        return sharedPreferences?.getString("currency", "Dollar") ?: "Dollar"
    }

    fun setSelectedCurrency(currency: String) {
        sharedPreferences?.edit()?.putString("currency", currency)?.apply()
    }
}