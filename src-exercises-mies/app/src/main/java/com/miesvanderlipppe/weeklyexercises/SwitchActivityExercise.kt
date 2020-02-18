package com.miesvanderlipppe.weeklyexercises

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SwitchActivityExercise : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_exercise)

        val b = getIntent().getExtras()
        if(b != null)
        {
            Log.d("DEBUG", "Found this: %d".format(b.getInt("id")))
        }
    }
}
