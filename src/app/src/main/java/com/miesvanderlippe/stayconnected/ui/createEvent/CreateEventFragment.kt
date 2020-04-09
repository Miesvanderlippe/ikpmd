package com.miesvanderlippe.stayconnected.ui.createEvent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miesvanderlippe.stayconnected.Events.CreateEvent

import com.miesvanderlippe.stayconnected.R
import com.miesvanderlippe.stayconnected.login.CheckLogin
import com.miesvanderlippe.stayconnected.modal.User
import kotlinx.android.synthetic.main.create_event_fragment.*

class CreateEventFragment : Fragment() {

    companion object {
        fun newInstance() = CreateEventFragment()
    }

    private lateinit var viewModel: CreateEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_event_fragment, container, false)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(CreateEventViewModel::class.java)
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        button.setOnClickListener {
            val userKey = CheckLogin(view.context).getUserToken()
            val userEmail = CheckLogin(view.context).getUserEmail()
            val user = User(userEmail, userKey, "None")
            val eventImage = textInputEditTextImg.text.toString()
            val eventName = textInputEditTextName.text.toString()
            val eventLoc = textInputEditTextLoc.text.toString()
            val eventDesc = textInputEditTextDesc.text.toString()
            val eventDate = textInputEditTextDate.text.toString()
            val eventId = ""
            CreateEvent(
                view.context,
                user,
                eventImage,
                eventName,
                eventLoc,
                eventDesc,
                eventDate,
                eventId
            ).createEvent(::callback)

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

}
