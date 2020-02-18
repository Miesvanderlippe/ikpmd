package com.miesvanderlipppe.weeklyexercises

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Get out button
        val button = findViewById<Button>(R.id.switchLayoutTestButton)
        // Set listener
        button.setOnClickListener{
            // Create intent to switch activities
            val intent = Intent(this, SwitchActivityExercise::class.java)

            // Bundles are used to share data between states.
            // They're a generic data collection with little typing, using a key-value store.
            // AKA a bitch to manage since you've got to remember keys.
            val b = Bundle()
            // Set our ID
            b.putInt("id", 69)

            // Link our bundle to our intent
            intent.putExtras(b)

            // Fire intent
            startActivity(intent)
        }
    }
}
