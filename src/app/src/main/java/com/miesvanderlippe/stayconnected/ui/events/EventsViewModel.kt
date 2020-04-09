package com.miesvanderlippe.stayconnected.ui.events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miesvanderlippe.stayconnected.repositories.EventData

class EventsViewModel: ViewModel() {
    var events = MutableLiveData<List<EventData>>()
}