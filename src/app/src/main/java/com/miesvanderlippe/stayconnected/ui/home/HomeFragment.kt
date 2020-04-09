package com.miesvanderlippe.stayconnected.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.miesvanderlippe.stayconnected.R
import com.miesvanderlippe.stayconnected.login.CheckLogin
import com.miesvanderlippe.stayconnected.login.FetchKey
import com.miesvanderlippe.stayconnected.login.InputValidation
import com.miesvanderlippe.stayconnected.modal.User
import com.miesvanderlippe.stayconnected.storage.DataStorage
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    val LOGKEY = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val status = CheckLogin(view.context).checkLogin()
        if (status) {
            Log.d(LOGKEY, "Already logged in")
            removeInterface()
        } else {
            Log.d(LOGKEY, "Not logged in, hiding logout button")
            appCompatButtonLogout.visibility = View.GONE
        }
        appCompatButtonLogin.setOnClickListener {
            // WIP
            Log.d(LOGKEY, "Pressed login")
            val user = User(textInputEditTextEmail.text.toString(), "None", textInputEditTextPassword.text.toString())
            if (verifyUser(view)) {
                Log.d(LOGKEY, "Trying to log in...")
                FetchKey(view.context, user).postRequest(::callback)
                CheckLogin(view.context).getUserName()
                CheckLogin(view.context).getUserEmail()

            }
        }
        appCompatButtonLogout.setOnClickListener {
            Log.d(LOGKEY, "Logging out user")
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
        Log.d(LOGKEY, "Login callback")
        if(result)
        {
            Log.d(LOGKEY, "Succes")
            removeInterface()
        }
        else
        {
            Log.d(LOGKEY, "Niet succes")
            text_input_error.text = getString(R.string.error_failed_auth)
        }
    }

    private fun removeInterface() {
        Log.d(LOGKEY, "Histing the login interface")
        textInputLayoutEmail.visibility = View.GONE
        textInputLayoutPassword.visibility = View.GONE
        appCompatButtonLogin.visibility = View.GONE
        appCompatButtonLogout.visibility = View.VISIBLE
        text_input_error.text = getString(R.string.wait_on_auth)
    }

    private fun showInterface() {
        Log.d(LOGKEY, "Showing the login interface")
        textInputLayoutEmail.visibility = View.VISIBLE
        textInputLayoutPassword.visibility = View.VISIBLE
        appCompatButtonLogin.visibility = View.VISIBLE
        appCompatButtonLogout.visibility = View.GONE
        text_input_error.text = getString(R.string.wait_on_auth)
    }

    private fun verifyUser(view: View) : Boolean {
        Log.d(LOGKEY, "Verifying form data")
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