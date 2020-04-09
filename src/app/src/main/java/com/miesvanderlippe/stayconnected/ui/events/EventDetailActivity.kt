package com.miesvanderlippe.stayconnected.ui.events

import com.miesvanderlippe.stayconnected.R

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class EventDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        val b = intent.extras
        if(b != null)
        {
            // Replace all this nonsense with a store of events + an ID.
            val eventName: TextView = findViewById(R.id.event_details_name)
            eventName.text = b.getString("title", "")

            val eventCover: ImageView = findViewById(R.id.event_details_image)

            Glide.with(this)
                .load(b.getString("image", ""))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(eventCover)
        }
    }
}