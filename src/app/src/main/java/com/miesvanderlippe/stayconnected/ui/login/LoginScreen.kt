package com.miesvanderlippe.stayconnected.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.miesvanderlippe.stayconnected.R
import com.miesvanderlippe.stayconnected.login.FetchKey
import com.miesvanderlippe.stayconnected.login.InputValidation
import com.miesvanderlippe.stayconnected.modal.User

class LoginScreen : AppCompatActivity(), View.OnClickListener {

    private val activity = this@LoginScreen

    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout

    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText

    private lateinit var inputValidation: InputValidation

    private lateinit var appCompatButtonLogin: AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)


    }

    private fun initViews() {

        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout

        textInputEditTextEmail = findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText

        appCompatButtonLogin = findViewById<View>(R.id.appCompatButtonLogin) as AppCompatButton

    }

    private fun initListeners() {
        appCompatButtonLogin!!.setOnClickListener(this)
    }

    private fun initObjects() {
        inputValidation = InputValidation(activity)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.appCompatButtonLogin -> verifyFromStorage()
            }
        }

    }

    private fun setUser(email: String, password: String): User {
        val user = User(email = email, password = password, key = "None")

        return user
    }

    private fun verifyFromStorage() {
        if (!inputValidation!!.checkInputEditTextFilled(textInputEditTextEmail!!, textInputLayoutEmail!!, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.checkInputEditTextEmail(textInputEditTextEmail!!, textInputLayoutEmail!!, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.checkInputEditTextFilled(textInputEditTextPassword!!, textInputLayoutPassword!!, getString(R.string.error_message_email))) {
            return
        }
        val user = setUser(textInputEditTextEmail.text.toString(), textInputEditTextPassword.text.toString())

        FetchKey(this, user)
    }

}