package com.miesvanderlippe.stayconnected.modal

//model class
data class User(val email: String, val key: String, val password: String)

data class Info(val full_name: String, val date_of_birth: String)

data class apiData (val success: String, val admin: String, val token: String, val user_model: Info)