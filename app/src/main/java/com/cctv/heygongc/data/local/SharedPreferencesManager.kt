package com.cctv.heygongc.data.local

import android.content.Context

class SharedPreferencesManager(private val context: Context) {

    val LOGIN_TOKEN = "LOGIN_TOKEN"
    val ACCESS_TOKEN = "ACCESS_TOKEN"
    val REFRESH_TOKEN = "REFRESH_TOKEN"
    val FCM_TOKEN = "FCM_TOKEN"

    private val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun loadData(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun removeData(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun clearAllData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}