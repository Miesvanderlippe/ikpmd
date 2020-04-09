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
    val PREFERENCE_USER_EMAIL = "Email"
    val PREFERENCE_USER_NAME = "Name"

    val userData = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setUserKey(userkey: String, useremail: String, username: String) {
        val editor = userData.edit()
        editor.putString(PREFERENCE_USER_EMAIL, useremail)
        editor.putString(PREFERENCE_USER_NAME, username)
        editor.putString(PREFERENCE_USER_KEY, userkey)
        editor.apply()

    }

    fun logoutUser(callback:() -> Unit) {
        val editor = userData.edit()
        editor.putString(PREFERENCE_USER_KEY, "None")
        editor.putString(PREFERENCE_USER_EMAIL, "None")
        editor.putString(PREFERENCE_USER_NAME, "None")
        editor.apply()
        callback()
    }

}