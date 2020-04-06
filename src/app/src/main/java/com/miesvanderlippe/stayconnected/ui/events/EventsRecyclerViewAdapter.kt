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


class EventsRecyclerViewAdapter(
    // Crazy constructor syntax. Ik begrijp er ook de ballen van.
    val rawEvents: EventsFragment.RawEvents,
    val context: Context
    ) : RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_testing_list_item, parent, false)
            val viewHolder = ViewHolder(view)

            return viewHolder
        }

        override fun getItemCount(): Int {
            return rawEvents.events.count()
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int)
        {
            val url = "http://stay-connected.miesvanderlippe.com/img/events/"
            holder.imageName.text = rawEvents.events[position].ActivityName
            holder.data = rawEvents.events[position]
            println(url + rawEvents.events[position].Image)
            Glide.with(this.context)
                .load(url + rawEvents.events[position].Image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image)
        }

        class ViewHolder: RecyclerView.ViewHolder
        {
            val image: ImageView
            val imageName: TextView
            val button: Button
            var data: EventsFragment.EventData

            constructor(itemView: View) : super(itemView) {
                image = itemView.findViewById(R.id.testing_layout_list_item_image)
                imageName = itemView.findViewById(R.id.testing_layout_list_item_name)
                button = itemView.findViewById(R.id.testing_layout_list_item_button)
                data = EventsFragment.EventData(0, "","",
                    "","","")

                button.setOnClickListener{

                    val intent = Intent(itemView.context, EventDetailActivity::class.java)
                    val b = Bundle()

                    b.putString("image", "http://stay-connected.miesvanderlippe.com/img/events/" + data.Image)
                    b.putString("title", data.ActivityName)

                    intent.putExtras(b)

                    // Fire intent
                    itemView.context.startActivity(intent)
                }
            }
        }
}