package com.miesvanderlippe.stayconnected.storage

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder

class DataStorage (val context: Context) {

    val PREFERENCE_NAME = "temp@replace.this"
    val PREFERENCE_USER_KEY = "testing_value_for_key"

    val userData = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getUserKey(): String? {
        return userData.getString(PREFERENCE_USER_KEY, "None")
    }

    fun setUserKey(userkey: String) {
        val key = ""
        val editor = userData.edit()
        editor.putString(PREFERENCE_USER_KEY, key)
        editor.apply()
    }

}