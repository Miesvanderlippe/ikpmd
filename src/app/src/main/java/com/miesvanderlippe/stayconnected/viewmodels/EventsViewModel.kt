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

}