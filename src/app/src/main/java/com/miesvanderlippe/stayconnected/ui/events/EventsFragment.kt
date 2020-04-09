package com.miesvanderlippe.stayconnected.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders.*
import androidx.recyclerview.widget.RecyclerView
import com.miesvanderlippe.stayconnected.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.miesvanderlippe.stayconnected.repositories.RemoteEventRespository


class EventsFragment: Fragment() {
    private lateinit var eventsViewModel: EventsViewModel
    private var remoteEventRepository: RemoteEventRespository? = null
    private var requestQue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        val adapter = EventsRecyclerViewAdapter(view.context)
        requestQue = Volley.newRequestQueue(view.context)
        remoteEventRepository = RemoteEventRespository(requestQue!!)

        remoteEventRepository!!.events.observe(viewLifecycleOwner, Observer { events ->
            // Update the cached copy of the words in the adapter.
            events?.let { adapter.updateEvents(it) }
        })

        remoteEventRepository!!.fetchJSON()

        val recyclerView: RecyclerView = view.rootView.findViewById(R.id.events_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
    }
}

