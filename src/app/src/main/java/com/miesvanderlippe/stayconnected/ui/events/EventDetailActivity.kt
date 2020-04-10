package com.miesvanderlippe.stayconnected.ui.events

import com.miesvanderlippe.stayconnected.R

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.miesvanderlippe.stayconnected.repositories.EventData

class EventDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        val b = intent.extras
        if(b != null)
        {
            // Replace all this nonsense with a store of events + an ID.
            val eventCover: ImageView = findViewById(R.id.event_details_image)
            val eventName: TextView = findViewById(R.id.event_details_name)
            val eventDate: TextView = findViewById(R.id.event_details_date)
            val eventDescription: TextView = findViewById(R.id.event_details_description)

            val eventData = b.getSerializable("event_data") as EventData

            eventName.text = eventData.ActivityName
            eventDate.text = eventData.EventDateTime
            eventDescription.text = eventData.Description

            Glide.with(this)
                .load("http://stay-connected.miesvanderlippe.com/img/events/" + eventData.Image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(eventCover)
        }
    }
}