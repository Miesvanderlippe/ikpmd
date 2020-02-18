package com.miesvanderlipppe.weeklyexercises

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SwitchActivityExercise : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_exercise)

        // Fetch our intent, read it's extra's
        val b = intent.extras
        // Do we have any values?
        if(b != null)
        {
            // Yes, log them
            Log.d("DEBUG", "Found this: %d".format(b.getInt("id")))
        }
    }
}
