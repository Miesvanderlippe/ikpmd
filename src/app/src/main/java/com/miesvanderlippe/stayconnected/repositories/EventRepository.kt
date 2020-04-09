package com.miesvanderlippe.stayconnected.repositories

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.JsonSerializer
import com.miesvanderlippe.stayconnected.data.EventDao
import com.miesvanderlippe.stayconnected.entities.EventEntitiy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class EventRepository {

    private val eventDao: EventDao
    private var requestQue: RequestQueue
    private var remoteEventRepository: RemoteEventRespository
    val LOGKEY = "EventRespository"

    constructor(lifecycle: LifecycleOwner, viewContext: Context, eventDao: EventDao) {
        Log.d(LOGKEY, "Creating new EventRespository")

        this.eventDao = eventDao
        this.allEvents = MutableLiveData()

        eventDao.getAllEvents().observe(lifecycle, Observer { events ->
                allEvents.value = events
            }
        )

        requestQue = Volley.newRequestQueue(viewContext)

        remoteEventRepository = RemoteEventRespository(requestQue)
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