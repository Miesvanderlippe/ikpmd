package com.miesvanderlippe.stayconnected.ui.events

import android.content.Context
import com.miesvanderlippe.stayconnected.R

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.miesvanderlippe.stayconnected.data.StayConDatabase
import com.miesvanderlippe.stayconnected.login.CheckLogin
import com.miesvanderlippe.stayconnected.repositories.EventData
import com.miesvanderlippe.stayconnected.repositories.EventRepository

class EventDetailActivity : AppCompatActivity() {
    lateinit var eventRepository: EventRepository
    var eventData: EventData? = null
    val LOGKEY = "EventDetailActivity"
    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_event_details)

        val b = intent.extras
        if(b != null)
        {
            // Replace all this nonsense with a store of events + an ID.
            val eventCover: ImageView = findViewById(R.id.event_details_image)
            val eventName: TextView = findViewById(R.id.event_details_name)
            val eventDate: TextView = findViewById(R.id.event_details_date)
            val eventDescription: TextView = findViewById(R.id.event_details_description)

            eventData = b.getSerializable("event_data") as EventData

            eventName.text = eventData?.ActivityName ?: "Not set"
            eventDate.text = eventData?.EventDateTime ?: "Not set"
            eventDescription.text = eventData?.Description ?: "Not set"

            Glide.with(this)
                .load("http://stay-connected.miesvanderlippe.com/img/events/" + eventData?.Image ?: "Not set")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(eventCover)
        }

        if(context != null)
        {
            Log.d(LOGKEY, "onCreate" + eventData?.ActivityName ?: "NotFound")
            val dao = StayConDatabase.getDatabase(context!!).eventDao()
            eventRepository = EventRepository(this, context!!, dao)

            val loginObj = CheckLogin(context!!)
            var userMail =  loginObj.getUserEmail()

            //Hide the button, because when there's no connection we don't care about it.
            findViewById<AppCompatButton>(R.id.event_details_join).visibility = View.GONE

            if (userMail !== "None") {
                  eventRepository.requestParticipationStatus(userMail, eventData!!.ID, ::updateParticipation)
            }
        }

        super.onCreate(savedInstanceState)
    }

    fun updateParticipation(nowParticipates: Boolean)
    {
        val button = findViewById<AppCompatButton>(R.id.event_details_join)
        button.visibility = View.VISIBLE

        val loginObj = CheckLogin(context!!)
        var userMail =  loginObj.getUserEmail()
        var userToken =  loginObj.getUserKey()

        if(nowParticipates)
        {
            button.text = "Verlaten"
            button.setOnClickListener {
                eventRepository.setParticipation(userMail, userToken?:"", eventData!!.ID, false, ::updateParticipation)
            }
        }
        else
        {
            button.text = "Deelnemen"
            button.setOnClickListener {
                eventRepository.setParticipation(userMail, userToken?:"", eventData!!.ID, true, ::updateParticipation)
            }
        }
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)

        if(eventData != null)
        {
            Log.d(LOGKEY, "setContentView" + eventData?.ActivityName ?: "NotFound")
        }
        else
        {
            Log.d(LOGKEY, "setContentView without eventData!!")
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        // onCreate View is called a few times to just save context for oncreate.
        this.context = context
        return super.onCreateView(name, context, attrs)
    }
}