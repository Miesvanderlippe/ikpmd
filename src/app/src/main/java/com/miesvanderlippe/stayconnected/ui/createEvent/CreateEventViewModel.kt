package com.miesvanderlippe.stayconnected.ui.createEvent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateEventViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is createEvent Fragment"
    }
    val text: LiveData<String> = _text
}
