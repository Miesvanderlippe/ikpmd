package com.miesvanderlippe.stayconnected.modal

//model class
data class User(val email: String, val key: String, val password: String)

data class apiData (val success: String, val token: String)