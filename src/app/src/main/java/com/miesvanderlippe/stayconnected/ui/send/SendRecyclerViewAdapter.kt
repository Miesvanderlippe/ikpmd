package com.miesvanderlippe.stayconnected.ui.send

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miesvanderlippe.stayconnected.R
import de.hdodenhof.circleimageview.CircleImageView


class SendRecyclerViewAdapter(
    // Crazy constructor syntax. Ik begrijp er ook de ballen van.
    context: Context,
    imageNames: ArrayList<String>,
    images: ArrayList<String>
) : RecyclerView.Adapter<SendRecyclerViewAdapter.ViewHolder>() {

    val mImageNames: ArrayList<String> = imageNames
    val mImages: ArrayList<String> = images
    val mContext: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_testing_list_item, parent, false)
        val viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return mImageNames.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        Glide.with(mContext)
            .asBitmap()
            .load(mImages.get(position))
            .into(holder.image)

        holder.imageName.text = mImageNames.get(position)

    }

    class ViewHolder: RecyclerView.ViewHolder
    {
        val image: CircleImageView
        val imageName: TextView

        constructor(itemView: View) : super(itemView) {
            image = itemView.findViewById(R.id.testing_layout_list_item_image)
            imageName = itemView.findViewById(R.id.testing_layout_list_item_name)
        }
    }
}