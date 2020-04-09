package com.miesvanderlippe.stayconnected.Events

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.miesvanderlippe.stayconnected.modal.User
import com.miesvanderlippe.stayconnected.modal.apiData
import com.miesvanderlippe.stayconnected.storage.DataStorage

class CreateEvent (
    val context: Context,
    val user: User,
    val eventImage: String,
    val eventName: String,
    val eventLoc: String,
    val eventDesc: String,
    val eventDate: String,
    val eventId: String
    ) {
    private val url = "http://stay-connected.miesvanderlippe.com/api?api_key=eVSLQUy3QNBm9HXkO9BsEPs09v2ZNA76c9byv9Pu&get=create_event"

    val queue = Volley.newRequestQueue(context)

    fun createEvent(callback:(result: Boolean) -> Unit) {

        val postRequest: StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { responseString ->
                val gson = GsonBuilder().create()
                val data = gson.fromJson(responseString, apiData::class.java)
                val dataStorage = DataStorage(context, user)
                if (data.success == "true") {
                    callback(true)
                } else {
                    Log.d("CreateEvent", "Failed to create event!")
                    println(data.toString())
                    callback(false)
                }

            },
            Response.ErrorListener { volleyError ->
                Log.d("Volley Error", volleyError.message)
            }) {

            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                if (eventId != "") {
                    params["event-id"] = eventId
                }
                params["email"] = user.email
                params["token"] = user.key
                params["image-event"] = eventImage
                params["name-event"] = eventName
                params["location-event"] = eventLoc
                params["desc-event"] = eventDesc
                params["date-event"] = eventDate
                return params
            }
        }
        queue.add(postRequest)
    }

}