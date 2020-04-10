package com.miesvanderlippe.stayconnected

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.miesvanderlippe.stayconnected.login.CheckLogin


class UpdateUsernameListener : DrawerLayout.DrawerListener{
    val LOGKEY = "UpdateUsernameListener"

    override fun onDrawerStateChanged(newState: Int) {
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
    }

    override fun onDrawerClosed(drawerView: View) {
    }

    override fun onDrawerOpened(drawerView: View) {
        Log.d(LOGKEY, "Drawer opened")
        val username = drawerView.findViewById<TextView>(R.id.header_user_name)
        val email = drawerView.findViewById<TextView>(R.id.header_user_email)


        var newUsername =  CheckLogin(drawerView.context).getUserName()
        var newEmail = CheckLogin(drawerView.context).getUserEmail()


        // CheckLogin could also just return nonetype, but doesn't. So stringcompare.
        if(newUsername == "None") {
            Log.d(LOGKEY, "Setting default texts")
            username.text = drawerView.context.getString(R.string.default_login_name)
            email.text = drawerView.context.getString(R.string.default_login_email)
        } else {
            Log.d(LOGKEY, "Setting username & email")
            username.text = newUsername
            email.text = newEmail
        }



    }
}


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_event_list, R.id.nav_create_event), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        drawerLayout.addDrawerListener(UpdateUsernameListener())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}
