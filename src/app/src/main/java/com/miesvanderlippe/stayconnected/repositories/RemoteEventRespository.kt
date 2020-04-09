package com.miesvanderlippe.stayconnected.repositories

import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.GsonBuilder

class RawEvents(
    val events: List<EventData>
)

class EventData(
    val ID: Int,
    val ActivityName: String,
    val activityLocation: String,
    val Description: String,
    val EventDateTime: Any,
    val Image: String
)

class RemoteEventRespository {
    private val url = "http://stay-connected.miesvanderlippe.com/api?api_key=eVSLQUy3QNBm9HXkO9BsEPs09v2ZNA76c9byv9Pu&get=events"
    var events = MutableLiveData<List<EventData>>()
    var requestQueue: RequestQueue

    constructor(requestQueue: RequestQueue) {
        this.requestQueue = requestQueue
    }

    fun fetchJSON() {
        println("Trying to fetch some data")
        val gson = GsonBuilder().create()
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener { responseString ->
                //Response
                val event = gson.fromJson(responseString, RawEvents::class.java)
                this.events.value = event.events
            },
            Response.ErrorListener { volleyError ->
                //Error
                println(volleyError.message)
            }
        )
        requestQueue.add(stringRequest)
    }
}