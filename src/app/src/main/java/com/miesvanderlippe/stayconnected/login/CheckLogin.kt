package com.miesvanderlippe.stayconnected.login

import android.content.Context

class CheckLogin (context: Context) {

    val PREFERENCE_NAME = "token"
    val PREFERENCE_USER_KEY = "Key"
    val PREFERENCE_USER_EMAIL = "Email"
    val PREFERENCE_USER_NAME = "Name"
    val PREFERENCE_USER_ISADMIN = "isAdmin"

    val userData = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getUserKey(): String? {
        return userData.getString(PREFERENCE_USER_KEY, "None")
    }

    fun getUserName(): String {
        return userData.getString(PREFERENCE_USER_NAME, "None").toString()
    }

    fun getUserEmail(): String {
        return userData.getString(PREFERENCE_USER_EMAIL, "None").toString()
    }

    fun getUserToken(): String {
        return userData.getString(PREFERENCE_USER_KEY, "None").toString()
    }

    fun getAdminStatus(): Boolean {
        return userData.getString(PREFERENCE_USER_ISADMIN, "None").toString() == "true"
    }

    fun checkLogin() : Boolean {
        return getUserKey() != "None"
    }
}