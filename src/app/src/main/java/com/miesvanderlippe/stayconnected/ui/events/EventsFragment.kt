package com.miesvanderlippe.stayconnected.ui.events

import android.content.Intent
import android.os.Bundle
import android.util.EventLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.miesvanderlippe.stayconnected.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.layout_testing_list_item.*
import org.json.JSONObject

class EventsFragment: Fragment() {
    private lateinit var eventsViewModel: EventsViewModel

    private val url = "http://stay-connected.miesvanderlippe.com/api?api_key=eVSLQUy3QNBm9HXkO9BsEPs09v2ZNA76c9byv9Pu&get=events"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_send, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchJSON(view)

    }

    class RawEvents(
        val events: List<EventData>
    )

    class EventData(
        val ID: Int,
        val ActivityName: String,
        val activityLocation: String,
        val Description: String,
        val EventDateTime: Any,
        val Image: String
    )

    fun fetchJSON(view: View) {
        println("Trying to fetch some data")
        val gson = GsonBuilder().create()
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {responseString ->
                //Response
                println(responseString)
                val event = gson.fromJson(responseString, RawEvents::class.java)
                val adapter = EventsRecyclerViewAdapter(event, view.context)
                val recyclerView: RecyclerView = view.rootView.findViewById(R.id.testing_recycler_view)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(view.context)
            },
            Response.ErrorListener {volleyError ->
                //Error
                println(volleyError.message)
                testing_layout_list_item_name.text = volleyError.message
            }
        )

        Volley.newRequestQueue(context).add(stringRequest)
    }
}

