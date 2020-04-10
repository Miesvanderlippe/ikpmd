package com.miesvanderlippe.stayconnected.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.GsonBuilder
import java.io.Serializable

class RawEvents(
    val events: List<EventData>
)

class EventData(
    val ID: Int,
    val ActivityName: String,
    val activityLocation: String,
    val Description: String,
    val EventDateTime: String,
    val Image: String
) : Serializable

class RemoteEventRespository {
    private val url = "http://stay-connected.miesvanderlippe.com/api?api_key=eVSLQUy3QNBm9HXkO9BsEPs09v2ZNA76c9byv9Pu&get=events"
    var events = MutableLiveData<List<EventData>>()
    var requestQueue: RequestQueue
    val LOGKEY = "RemoteEventRespository"

    constructor(requestQueue: RequestQueue) {
        Log.d(LOGKEY, "Creating new instance")
        this.requestQueue = requestQueue
    }

    fun fetchJSON() {
        Log.d(LOGKEY, "Queuing new data-fetch")
        val gson = GsonBuilder().create()
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener { responseString ->
                //Response
                Log.d(LOGKEY, "Got valid response")
                val event = gson.fromJson(responseString, RawEvents::class.java)
                this.events.value = event.events
            },
            Response.ErrorListener { volleyError ->
                Log.d(LOGKEY, "Got volley error!:\n" + volleyError.message)
            }
        )
        requestQueue.add(stringRequest)
    }
}