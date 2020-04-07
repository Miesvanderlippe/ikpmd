package com.miesvanderlippe.stayconnected.login

import android.content.Context
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
    private val url = "http://stay-connected.miesvanderlippe.com/api?api_key=eVSLQUy3QNBm9HXkO9BsEPs09v2ZNA76c9byv9Pu&get=get_token"

    val status : String = ""
    private var key : String = ""


    fun postRequest(url: String) {

        val queue = Volley.newRequestQueue(context)

        val params = HashMap<String, String>()
        params["email"] = user.email
        params["password"] = user.password
        val jsonObject = JSONObject(params as Map<String, String>)

        val postRequest = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            Response.Listener { responseString ->
                println(responseString)
            },
            Response.ErrorListener { volleyError ->
                println(volleyError.message)
            })
        queue.add(postRequest)

    }

//    val stringRequest = StringRequest(
//        Request.Method.POST,
//        url,
//        Response.Listener { responseString ->
//            println(responseString)
//            val gson = GsonBuilder().create()
//            val userToken = gson.fromJson(responseString, apiData::class.java)
//            if (userToken.success == "true") {
//                this.key = userToken.token
//            } else {
//                println("Auth failed")
//            }
//        },
//        Response.ErrorListener { volleyError ->
//            println(volleyError.message)
//        }
//
//    )

}