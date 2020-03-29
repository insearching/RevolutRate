package com.insearching.revolutrate.data.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insearching.revolutrate.ui.Rate

class SharedPrefsHelper(private val preferences: SharedPreferences) {
    
    companion object {
        private const val RATES_INFO = "rates_info"
    }
    
    fun saveRatesInfo(info: Map<String, Rate>) {
        preferences.edit {
            putString(RATES_INFO, Gson().toJson(info))
        }
    }
    
    fun loadRatesInfo(): Map<String, Rate>? {
        return preferences.getString(RATES_INFO, null)?.let {
            Gson().fromJson<Map<String, Rate>>(it, object : TypeToken<Map<String, Rate>?>() {}.type)
        }
    }
}