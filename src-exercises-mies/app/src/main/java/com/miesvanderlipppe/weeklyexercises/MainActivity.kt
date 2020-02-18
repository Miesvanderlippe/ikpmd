package com.miesvanderlipppe.weeklyexercises

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.util.Log


class MainActivity : AppCompatActivity() {
    // Constants are only allowed in top-level objects.
    // This is the way to wrap them according to kotlin docs.
    companion object {
        const val PREFS_FILE = "Voorkeuren"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get out button
        val button = findViewById<Button>(R.id.switchLayoutTestButton)
        val switchToEditorButton = findViewById<Button>(R.id.go_to_model_editor_button)

        // Do some settings stuff
        val settings = getSharedPreferences(MainActivity.PREFS_FILE, 0)

        // We get a settings editor
        val editor = settings.edit()
        editor.putString("prefs_test", "This is a test")
        editor.apply() // Save

        // Read from our preferences
        val settingsReader = getSharedPreferences(MainActivity.PREFS_FILE, 0)
        val testRead = settingsReader.getString("prefs_test", "not-set")
        Log.d("DEBUG", "Found this: %s".format(testRead))

        // Go to model demo
        switchToEditorButton.setOnClickListener{
            val intent = Intent(this, EnterDetails::class.java)
            startActivity(intent)
        }

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
