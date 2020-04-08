package com.miesvanderlippe.stayconnected.ui.home

import android.os.Bundle
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
        appCompatButtonLogin.setOnClickListener {
            // WIP
            val user = User(textInputEditTextEmail.text.toString(), "None", textInputEditTextPassword.text.toString())
            if (verifyUser(user, view)) {
                FetchKey(view.context, user).postRequest()
            }
        }
        val status = CheckLogin(view.context).checkLogin()
        if (!status) {
            text_input_error.text = getString(R.string.error_failed_auth)
        } else {
            text_input_error.text = getString(R.string.logged_in)
        }
        super.onViewCreated(view, savedInstanceState)

    }

    private fun verifyUser(user: User, view: View) : Boolean {
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