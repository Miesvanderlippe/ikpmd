package com.miesvanderlippe.stayconnected.repositories

import androidx.lifecycle.LiveData
import com.miesvanderlippe.stayconnected.data.EventDao
import com.miesvanderlippe.stayconnected.entities.EventEntitiy

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class EventRepository(private val eventDao: EventDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allEvents: LiveData<List<EventEntitiy>> = eventDao.getAllEvents()

    suspend fun insert(event: EventEntitiy) {
        eventDao.insert(event)
    }
}