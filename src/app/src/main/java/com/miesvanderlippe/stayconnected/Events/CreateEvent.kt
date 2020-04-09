package com.miesvanderlippe.stayconnected.Events

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.miesvanderlippe.stayconnected.modal.User
import com.miesvanderlippe.stayconnected.modal.apiData
import com.miesvanderlippe.stayconnected.storage.DataStorage
import java.io.IOException

class CreateEvent (
    val context: Context,
    val user: User,
    val eventName: String,
    val eventLoc: String,
    val eventDesc: String,
    val eventDate: String,
    val eventId: String
    ) {
    private lateinit var imageView: ImageView
    private var imageData: ByteArray? = null
    private val url = "http://stay-connected.miesvanderlippe.com/api?api_key=eVSLQUy3QNBm9HXkO9BsEPs09v2ZNA76c9byv9Pu&get=create_event"

    val queue = Volley.newRequestQueue(context)

    fun createEvent(callback:(result: Boolean) -> Unit) {

        val postRequest: StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { responseString ->
                val gson = GsonBuilder().create()
                val data = gson.fromJson(responseString, apiData::class.java)
                val dataStorage = DataStorage(context, user)
                if (data.success == "true") {
                    callback(true)
                } else {
                    Log.d("CreateEvent", "Failed to create event!")
                    println(data.toString())
                    callback(false)
                }

            },
            Response.ErrorListener { volleyError ->
                Log.d("Volley Error", volleyError.message)
            }) {

            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                if (eventId != "") {
                    params["event-id"] = eventId
                }
                params["email"] = user.email
                params["token"] = user.key
                params["image-event"] = eventImage
                params["name-event"] = eventName
                params["location-event"] = eventLoc
                params["desc-event"] = eventDesc
                params["date-event"] = eventDate
                return params
            }
        }
        queue.add(postRequest)
    }

    fun uploadImage(context: Context, callback:(result: Boolean) -> Unit) {
        imageData?: return
        val request = object : VolleyFileUploadRequest(
            Method.POST,
            url,
            Response.Listener {
                println("response is: $it")
            },
            Response.ErrorListener {
                println("error is: $it")
            }
        ) {
            override fun getByteData(): MutableMap<String, FileDataPart> {
                var params = HashMap<String, FileDataPart>()
                params["image-event"] = FileDataPart("image", imageData!!, "jpeg")
                return params
            }
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                if (eventId != "") {
                    params["event-id"] = eventId
                }
                params["email"] = user.email
                params["token"] = user.key
                params["name-event"] = eventName
                params["location-event"] = eventLoc
                params["desc-event"] = eventDesc
                params["date-event"] = eventDate
                return params
            }
        }
        Volley.newRequestQueue(context).add(request)
    }

    @Throws(IOException::class)
    fun createImageData(uri: Uri) {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
    }

}