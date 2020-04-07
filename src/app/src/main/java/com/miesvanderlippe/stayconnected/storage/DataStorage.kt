package com.miesvanderlippe.stayconnected.storage

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.miesvanderlippe.stayconnected.modal.User

class DataStorage (val context: Context, val user: User) {

    val PREFERENCE_NAME = "token"
    val PREFERENCE_USER_KEY = "Key"

    val userData = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setUserKey(userkey: String) {
        val editor = userData.edit()
        editor.putString(PREFERENCE_USER_KEY, userkey)
        editor.apply()
    }

}