package com.miesvanderlippe.stayconnected.Events

import android.content.Context
import android.net.Uri

class CreateImageData {
    fun createImageData(context: Context, uri: Uri) : ByteArray {
        var imageData : ByteArray? = null
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
        return imageData!!
    }
}