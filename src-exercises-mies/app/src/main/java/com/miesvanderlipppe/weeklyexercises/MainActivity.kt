package com.miesvanderlipppe.weeklyexercises

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.switchLayoutTestButton)
        button.setOnClickListener{
            val intent = Intent(this, SwitchActivityExercise::class.java)

            val b = Bundle()
            b.putInt("id", 69)

            intent.putExtras(b)

            startActivity(intent)
        }
    }
}
