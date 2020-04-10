package com.miesvanderlippe.stayconnected.ui.createEvent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.miesvanderlippe.stayconnected.Events.CreateEvent
import com.miesvanderlippe.stayconnected.Events.CreateImageData
import com.miesvanderlippe.stayconnected.R
import com.miesvanderlippe.stayconnected.data.CreateEventCache
import com.miesvanderlippe.stayconnected.login.CheckLogin
import com.miesvanderlippe.stayconnected.modal.Event
import com.miesvanderlippe.stayconnected.modal.User
import kotlinx.android.synthetic.main.create_event_fragment.*
import java.io.ByteArrayOutputStream


class CreateEventFragment : Fragment() {


    private lateinit var viewModel: CreateEventViewModel
    private var imageData: ByteArray? = null
    var LOGTAG = "CreateEventFragment"

    companion object {
        private const val IMAGE_PICK_CODE = 999
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_event_fragment, container, false)
    }

    override fun onStop() {
        super.onStop()
        cacheEvent()
    }

    override fun onStart() {
        super.onStart()
        loadCached()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateEventViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!CheckLogin(view.context).checkLogin()) {
            name_event.visibility = View.GONE
            description_event.visibility = View.GONE
            date_event.visibility = View.GONE
            location_event.visibility = View.GONE
            upload_image_button.visibility = View.GONE
            save_event_button.visibility = View.GONE
        } else {
            not_logged_in.visibility = View.GONE
        }
        imageView2.visibility = View.GONE
        upload_image_button.setOnClickListener {
            selectImage()
            imageView2.visibility = View.VISIBLE
        }

        save_event_button.setOnClickListener {
            Log.d(LOGTAG, "Buttonclick")
            val userKey = CheckLogin(view.context).getUserToken()
            val userEmail = CheckLogin(view.context).getUserEmail()
            val user = User(userEmail, userKey, "None")
            val eventName = name_event.text.toString()
            val eventLoc = location_event.text.toString()
            val eventDesc = description_event.text.toString()
            val eventDate = date_event.text.toString()
            val eventId = ""

            val event = CreateEvent(
                view.context,
                user,
                eventName,
                eventLoc,
                eventDesc,
                eventDate,
                eventId
            )
            event.imageData = imageData
            event.uploadImage(view.context, ::callback)
            Snackbar.make(view, "Event aangemaakt", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            name_event.text = null
            description_event.text = null
            date_event.text = null
            location_event.text = null
            imageView2.visibility = View.GONE
            CreateEventCache(view.context).destroyEvent()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun callback(result: Boolean) {
        if (result) {
            Log.d(LOGTAG,"event created")


        } else {
            Log.d(LOGTAG, "failed")
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            Log.d(LOGTAG, "RESULT OK")
            val uri = data?.data
            Log.d(LOGTAG, "URI" + uri.toString())
            if (uri != null) {
                Log.d(LOGTAG, "URI NOT NULL")
                imageView2.setImageURI(uri)
                Log.d(LOGTAG, "Set the fucking imageview (never happens yolo)")
                imageData = CreateImageData().createImageData(this.context!!, uri)
            }
        }
        else
        {
            Log.d(LOGTAG, "RESULT NOT OK" + resultCode)
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun imageToString(bitmap: Bitmap) : String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imgBytes : ByteArray = outputStream.toByteArray()
        return Base64.encodeToString(imgBytes, Base64.DEFAULT)
    }

    private fun cacheEvent() {
        Log.d(LOGTAG, "Trying to cache the event")
        val event = Event(name_event.text.toString(), description_event.text.toString(), date_event.text.toString(), location_event.text.toString())
        CreateEventCache(this.context!!).cacheEvent(event)
    }

    private fun loadCached() {
        val cachedEvent : Event = CreateEventCache(this.context!!).loadEvent()
        name_event.setText(cachedEvent.eventName)
        description_event.setText(cachedEvent.eventDesc)
        date_event.setText(cachedEvent.eventDate)
        location_event.setText(cachedEvent.eventLoc)
    }

}
