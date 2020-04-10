package com.miesvanderlippe.stayconnected.ui.events

import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.miesvanderlippe.stayconnected.R
import java.io.InputStream
import java.net.URL
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.miesvanderlippe.stayconnected.repositories.EventData


class EventsRecyclerViewAdapter(
    // Crazy constructor syntax. Ik begrijp er ook de ballen van.
    val context: Context
    ) : RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {
        var events = emptyList<EventData>()
        val LOGTAG = "EventsRecyclerViewAdapter"

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_testing_list_item, parent, false)
            val viewHolder = ViewHolder(view)

            return viewHolder
        }

        override fun getItemCount(): Int {
            return events.count()
        }

        fun updateEvents(events: List<EventData>) {
            this.events = events
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int)
        {
            Log.d(LOGTAG, "Binding viewholder for: " + events[position].ActivityName)
            val url = "http://stay-connected.miesvanderlippe.com/img/events/"
            holder.imageName.text = events[position].ActivityName
            holder.data = events[position]
            println(url + events[position].Image)
            Glide.with(this.context)
                .load(url + events[position].Image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image)
        }

        class ViewHolder: RecyclerView.ViewHolder
        {
            val image: ImageView
            val imageName: TextView
            val button: Button
            var data: EventData
            val LOGTAG = "ViewHolder"

            constructor(itemView: View) : super(itemView) {
                image = itemView.findViewById(R.id.testing_layout_list_item_image)
                imageName = itemView.findViewById(R.id.testing_layout_list_item_name)
                button = itemView.findViewById(R.id.testing_layout_list_item_button)
                data = EventData(0, "","",
                    "","","")

                Log.d(LOGTAG, "Setting buttonclick handler for: " + data.ActivityName)
                button.setOnClickListener{

                    val intent = Intent(itemView.context, EventDetailActivity::class.java)
                    val b = Bundle()
                    Log.d(LOGTAG, "Firing buttonclick for: " + data.ActivityName)
                    b.putSerializable("event_data", data)

                    intent.putExtras(b)

                    // Fire intent
                    itemView.context.startActivity(intent)
                }
            }
        }
}