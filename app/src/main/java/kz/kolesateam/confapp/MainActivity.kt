package kz.kolesateam.confapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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


        setContentView(R.layout.activity_main)

        val nameEditText: EditText = findViewById(R.id.activity_main_editText_name)

        openHelloButton.setOnClickListener {
            saveUserName(nameEditText.text.toString())
            navigateToHelloScreen()
        }

        nameEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (!p0.isNullOrBlank() && !p0.isNullOrEmpty()) {
                    openHelloButton.setBackgroundColor(0xFF2582DE.toInt())
                    openHelloButton.setTextColor(0xFFFFFFFF.toInt())
                    openHelloButton.isEnabled = true
                }
                else if(p0.isNullOrBlank() && p0.isNullOrEmpty()){
                    openHelloButton.setBackgroundColor(0xFFE9E8E9.toInt())
                    openHelloButton.setTextColor(0xFF797777.toInt())
                    openHelloButton.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })


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