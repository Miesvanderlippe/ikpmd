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

    var imageData: ByteArray? = null
    private val url = "http://stay-connected.miesvanderlippe.com/api?api_key=eVSLQUy3QNBm9HXkO9BsEPs09v2ZNA76c9byv9Pu&get=create_event"
    var LOGTAG = "CreateEvent"

    fun uploadImage(context: Context, callback:(result: Boolean) -> Unit) {
        Log.d(LOGTAG, "function called")

        val request = object : VolleyFileUploadRequest(
            Method.POST,
            url,
            Response.Listener {
                Log.d(LOGTAG, "Got response" + it.statusCode)

                println("response is: $it")
            },
            Response.ErrorListener {
                for(element in it.stackTrace)
                {
                    Log.d(LOGTAG, "Stackstrace" + element.toString())
                }
                println("error is: $it")
            }
        ) {
            override fun getByteData(): MutableMap<String, FileDataPart> {
                Log.d(LOGTAG, "Getting bytedata")
                if(imageData == null)
                {
                    Log.d(LOGTAG, "Nahh")
                }
                var params = HashMap<String, FileDataPart>()
                params["image-event"] = FileDataPart("image", imageData!!, "jpeg")
                Log.d(LOGTAG, "Returning bytedata")
                return params
            }
            override fun getParams(): Map<String, String> {
                Log.d(LOGTAG, "Getting params")
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
        Log.d(LOGTAG, "adding to queueue")
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