package com.miesvanderlippe.stayconnected.ui.createEvent

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.volley.toolbox.Volley
import com.miesvanderlippe.stayconnected.Events.CreateEvent
import com.miesvanderlippe.stayconnected.Events.CreateImageData
import com.miesvanderlippe.stayconnected.Events.VolleyFileUploadRequest
import com.miesvanderlippe.stayconnected.R
import com.miesvanderlippe.stayconnected.login.CheckLogin
import com.miesvanderlippe.stayconnected.modal.User
import kotlinx.android.synthetic.main.create_event_fragment.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.io.IOException


class CreateEventFragment : Fragment() {

    private var IMG_REQUEST: Int = 1

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateEventViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        upload_image_button.setOnClickListener {
            selectImage()
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

        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun callback(result: Boolean) {
        if (result) {
            println("event created")
        } else {
            println("failed")
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

}
