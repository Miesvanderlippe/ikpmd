package com.miesvanderlippe.stayconnected.repositories

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.google.gson.JsonSerializer
import com.miesvanderlippe.stayconnected.data.EventDao
import com.miesvanderlippe.stayconnected.entities.EventEntitiy
import com.miesvanderlippe.stayconnected.modal.apiData
import com.miesvanderlippe.stayconnected.storage.DataStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class EventRepository {

    private val eventDao: EventDao
    private var requestQue: RequestQueue
    private val lifecycle: LifecycleOwner

    val LOGKEY = "EventRespository"

    constructor(lifecycle: LifecycleOwner, viewContext: Context, eventDao: EventDao) {
        Log.d(LOGKEY, "Creating new EventRespository")

        this.eventDao = eventDao
        this.allEvents = MutableLiveData()
        this.lifecycle = lifecycle

        eventDao.getAllEvents().observe(lifecycle, Observer { events ->
                allEvents.value = events
            }
        )

        requestQue = Volley.newRequestQueue(viewContext)


    }

    fun queueRefresh()
    {
        val remoteEventRepository = RemoteEventRespository(requestQue)
        remoteEventRepository.fetchJSON()

        remoteEventRepository.events.observe(lifecycle, Observer { eventObjs ->
            Log.d(LOGKEY, "Received new events from API!")
            if(eventObjs !== null)
            {
                this.allEvents.value = eventObjs.map { event ->
                    Log.d(LOGKEY, "Adding the following event: " + event.ID.toString() + event.ActivityName + event.activityLocation + event.Description + "Non" + event.Image )
                    EventEntitiy(event.ID, event.ActivityName ?: "", event.activityLocation ?: "", event.Description ?: "",
                        event.EventDateTime ?: "", event.Image, false)
                }
                GlobalScope.launch()
                {
                    Log.d(LOGKEY, "Ordering cache-refresh")
                    refreshCache()
                }
            }
        })
    }

    private class ParticipationResponse(
        val participates: Boolean
    )

    fun requestParticipationStatus(userEmail: String, eventId: Int, callback:(result: Boolean) -> Unit)
    {
        val gson = GsonBuilder().create()
        val stringRequest = StringRequest(
            Request.Method.GET,
                "http://stay-connected.miesvanderlippe.com/api?api_key=eVSLQUy3QNBm9HXkO9BsEPs09v2ZNA76c9byv9Pu&get=get_participation&email="+userEmail+"&activity_id="+eventId.toString(),
            Response.Listener { responseString ->
                //Response
                Log.d(LOGKEY, "Got valid response")
                Log.d(LOGKEY, "Response: " + responseString)
                val participates = gson.fromJson(responseString, ParticipationResponse::class.java)
                callback(participates.participates)
            },
            Response.ErrorListener { volleyError ->
                Log.d(LOGKEY, "Got volley error!:\n" + volleyError.message)
            }
        )
        requestQue.add(stringRequest)
    }

    fun setParticipation(userEmail: String, userToken: String, eventId: Int, participate: Boolean, callback:(result: Boolean) -> Unit)
    {
        val postRequest : StringRequest = object : StringRequest(
            Request.Method.POST,
            "http://stay-connected.miesvanderlippe.com/api?api_key=eVSLQUy3QNBm9HXkO9BsEPs09v2ZNA76c9byv9Pu&get=set_participation",

            Response.Listener <String> { responseString ->
                val gson = GsonBuilder().create()
                Log.d(LOGKEY, responseString)
                val data = gson.fromJson(responseString, ParticipationResponse::class.java)
                callback(data.participates)
            },
            Response.ErrorListener { volleyError ->
                Log.d("Volley Error", volleyError.message)
            })
        {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = userEmail
                params["token"] = userToken
                params["event-id"] = eventId.toString()
                params["participate"] = participate.toString()
                return params
            }
        }

        requestQue.add(postRequest)
    }

    suspend fun refreshCache()
    {
        Log.d(LOGKEY, "Starting cache-refresh (deleting old cache)")
        eventDao.deleteAll()
        if(this.allEvents.value !== null)
        {
            Log.d(LOGKEY, "Found new values")
            for(item in this.allEvents.value!!)
            {
                eventDao.insert(item)
            }
        }
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allEvents: MutableLiveData<List<EventEntitiy>>

    suspend fun insert(event: EventEntitiy) {
        eventDao.insert(event)
    }
}