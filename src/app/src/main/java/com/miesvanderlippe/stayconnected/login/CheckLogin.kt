package com.miesvanderlippe.stayconnected.login

import android.content.Context

class CheckLogin (context: Context) {

    val PREFERENCE_NAME = "token"
    val PREFERENCE_USER_KEY = "Key"
    val PREFERENCE_USER_EMAIL = "Email"
    val PREFERENCE_USER_NAME = "Name"

    val userData = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    private fun getUserKey(): String? {
        return userData.getString(PREFERENCE_USER_KEY, "None")
    }

    fun getUserName(): String {
        return userData.getString(PREFERENCE_USER_NAME, "None").toString()
    }

    fun getUserEmail(callback:(userEmail: String) -> Unit) {
        val useremail = userData.getString(PREFERENCE_USER_EMAIL, "None")
        if (useremail != null) {
            callback(useremail)
        } else {
            callback("Niemand")
        }

    }


    fun checkLogin() : Boolean {
        val loggedIn = getUserKey()
        return loggedIn != "None"
    }
}