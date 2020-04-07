package com.miesvanderlippe.stayconnected.login

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.miesvanderlippe.stayconnected.modal.User
import com.miesvanderlippe.stayconnected.modal.apiData
import com.miesvanderlippe.stayconnected.storage.DataStorage
import org.json.JSONObject

class FetchKey (val context: Context, val user: User){


    val status : String = ""
    private var key : String = ""
    var test = postRequest()

    fun postRequest() {
        val url = "http://stay-connected.miesvanderlippe.com/api?api_key=eVSLQUy3QNBm9HXkO9BsEPs09v2ZNA76c9byv9Pu&get=get_token"
        val queue = Volley.newRequestQueue(context)


        val postRequest : StringRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener <String> { responseString ->
                val gson = GsonBuilder().create()
                val data = gson.fromJson(responseString, apiData::class.java)
                if (data.success == "true") {
                    val dataStorage = DataStorage(context, user)
                    dataStorage.setUserKey(data.token)
                } else {
                    Log.d("Auth", "Auth failed!")
                }

            },
            Response.ErrorListener { volleyError ->
                println(volleyError.message)
            }) {

            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = user.email
                params["password"] = user.password
                return params
            }
        }
        queue.add(postRequest)

    }

}