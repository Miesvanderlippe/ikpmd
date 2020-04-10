package com.miesvanderlippe.stayconnected.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.miesvanderlippe.stayconnected.modal.Event

class CreateEventCache (
    context: Context
) {
    private var isCached = false
    val LOGTAG = "CreateEventCache"
    val EmptyObjStr = "{\"eventName\":\"\",\"eventDesc\":\"\",\"eventDate\":\"\",\"eventLoc\":\"\"}"
    val PREFERENCE_EVENT_OBJECT = "CachedEvent"
    val cached = context.getSharedPreferences(PREFERENCE_EVENT_OBJECT, Context.MODE_PRIVATE)
    val cachedEditor = cached.edit()
    val gson = Gson()

    fun cacheEvent(event: Event) {
        Log.d(LOGTAG, "Trying to write cache")
        val json = gson.toJson(event)
        cachedEditor.putString(PREFERENCE_EVENT_OBJECT, json)
        cachedEditor.commit()
        isCached = true
    }

    fun loadEvent() : Event {
        Log.d(LOGTAG, "Trying to write cache")
        val json = cached.getString(PREFERENCE_EVENT_OBJECT, EmptyObjStr)
        return gson.fromJson(json, Event::class.java)
    }

    fun destroyEvent() {
        Log.d(LOGTAG, "Trying to remove cached event")
        cachedEditor.putString(PREFERENCE_EVENT_OBJECT, EmptyObjStr)
        isCached = false
    }
}