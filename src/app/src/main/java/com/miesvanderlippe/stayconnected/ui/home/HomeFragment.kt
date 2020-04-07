package com.miesvanderlippe.stayconnected.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.miesvanderlippe.stayconnected.R
import com.miesvanderlippe.stayconnected.login.FetchKey
import com.miesvanderlippe.stayconnected.login.InputValidation
import com.miesvanderlippe.stayconnected.modal.User
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
            val validate = InputValidation(view.context)
            val user = User(textInputEditTextEmail.text.toString(), "None", textInputEditTextPassword.text.toString())
            FetchKey(view.context, user)
        }
        super.onViewCreated(view, savedInstanceState)

    }


}