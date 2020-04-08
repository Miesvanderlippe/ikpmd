package com.miesvanderlippe.stayconnected.login

import android.content.Context

class CheckLogin (context: Context) {

    val PREFERENCE_NAME = "token"
    val PREFERENCE_USER_KEY = "Key"

    val userData = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    private fun getUserKey(): String? {
        return userData.getString(PREFERENCE_USER_KEY, "None")
    }

    fun checkLogin() : Boolean {
        val loggedIn = getUserKey()
        return loggedIn != "None"
    }
}