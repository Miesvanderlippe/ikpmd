package com.miesvanderlipppe.weeklyexercises

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.miesvanderlipppe.weeklyexercises.models.TestingModel

class EnterDetails : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "EnterDetailsView"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_details)

        val switchToEditorButton = findViewById<Button>(R.id.save_model_button)

        switchToEditorButton.setOnClickListener{
            val editText = findViewById<EditText>(R.id.editText) // Consistent naming is important
            val testingModel = TestingModel()
            testingModel.testingString = editText.text.toString()

            if(testingModel.testingString.isNotEmpty())
            {
                Snackbar.make(this.findViewById(android.R.id.content), "Naam: %s".format(testingModel.testingString), Snackbar.LENGTH_LONG).show()
            }
            else
            {
                Snackbar.make(this.findViewById(android.R.id.content), "Geen waarde ingevoerd", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
