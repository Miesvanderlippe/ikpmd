package com.miesvanderlippe.stayconnected.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.miesvanderlippe.stayconnected.data.StayConDatabase
import com.miesvanderlippe.stayconnected.entities.EventEntitiy
import com.miesvanderlippe.stayconnected.repositories.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EventsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EventRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allEvents: LiveData<List<EventEntitiy>>

    init {
        val eventDao = StayConDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventDao)
        allEvents = repository.allEvents

    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(event: EventEntitiy) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(event)
    }
}