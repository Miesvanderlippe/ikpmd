package com.miesvanderlippe.stayconnected.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.miesvanderlippe.stayconnected.R
import com.miesvanderlippe.stayconnected.login.CheckLogin
import com.miesvanderlippe.stayconnected.login.FetchKey
import com.miesvanderlippe.stayconnected.login.InputValidation
import com.miesvanderlippe.stayconnected.modal.User
import com.miesvanderlippe.stayconnected.storage.DataStorage
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_main.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var username: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val status = CheckLogin(view.context).checkLogin()
        if (status) {
            removeInterface()
        } else {
            appCompatButtonLogout.visibility = View.GONE
        }
        appCompatButtonLogin.setOnClickListener {
            // WIP
            val user = User(textInputEditTextEmail.text.toString(), "None", textInputEditTextPassword.text.toString())
            if (verifyUser(view)) {
                FetchKey(view.context, user).postRequest(::callback)
                CheckLogin(view.context).getUserName()
                CheckLogin(view.context).getUserEmail(::userEmail)

            }
        }
        appCompatButtonLogout.setOnClickListener {
            val dataStorage = DataStorage(view.context, User(email = "None", password = "None", key = "None"))
            dataStorage.logoutUser(::showInterface)
        }
        super.onViewCreated(view, savedInstanceState)

    }

    private fun userName(result: String) {
        text_input_error.text = "Ingelogd als: " + result

    }
    private fun userEmail(result: String) {
        return
    }

    private fun callback(result: Boolean)
    {
        if(result)
        {
            Log.d("LOGIN", "Succes")
            removeInterface()
        }
        else
        {
            Log.d("LOGIN", "Niet succes")
            text_input_error.text = getString(R.string.error_failed_auth)
        }
    }

    private fun removeInterface() {
        textInputLayoutEmail.visibility = View.GONE
        textInputLayoutPassword.visibility = View.GONE
        appCompatButtonLogin.visibility = View.GONE
        appCompatButtonLogout.visibility = View.VISIBLE

    }

    private fun showInterface() {
        textInputLayoutEmail.visibility = View.VISIBLE
        textInputLayoutPassword.visibility = View.VISIBLE
        appCompatButtonLogin.visibility = View.VISIBLE
        appCompatButtonLogout.visibility = View.GONE
        text_input_error.text = getString(R.string.wait_on_auth)
    }

    private fun verifyUser(view: View) : Boolean {
        val validate = InputValidation(view.context)

        if (!validate.checkInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return false
        }

        if (!validate.checkInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return false
        }
        return true

    }




}