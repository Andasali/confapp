package kz.kolesateam.confapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.hello.presentation.HelloActivity
import kz.kolesateam.confapp.hello.presentation.TestHelloActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navigateToHelloScreen()

    }

    private fun navigateToHelloScreen() {
        val helloScreenIntent = Intent(this, HelloActivity::class.java)
        startActivity(helloScreenIntent)
    }
}