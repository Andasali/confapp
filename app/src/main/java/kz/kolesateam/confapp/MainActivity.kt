package kz.kolesateam.confapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.hello.presentation.HelloActivity

const val USER_NAME_KEY = "user_name"
const val APPLICATION_SHARED_PREFERENCES = "application"

class MainActivity : AppCompatActivity() {

    private val openHelloButton: Button by lazy {
        findViewById(R.id.activity_main_continue_button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isUserSaved()){
            navigateToHelloScreen()
            finish()
        }

        setContentView(R.layout.activity_main)

        val nameEditText: EditText = findViewById(R.id.activity_main_editText_name)

        openHelloButton.setOnClickListener {
            saveUserName(nameEditText.text.toString())
            navigateToHelloScreen()
        }




    }

    private fun isUserSaved() : Boolean  {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
                APPLICATION_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
        )

        return sharedPreferences.contains(USER_NAME_KEY)
    }

    private fun saveUserName(userName: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
                APPLICATION_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString(USER_NAME_KEY, userName)
        editor.apply()
    }

    private fun navigateToHelloScreen() {
        val helloScreenIntent = Intent(this, HelloActivity::class.java)
        startActivity(helloScreenIntent)
    }
}