package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R

const val USER_NAME_KEY = "user_name"
const val APPLICATION_SHARED_PREFERENCES = "application"

class HelloActivity : AppCompatActivity() {
    private val openHelloButton: Button by lazy {
        findViewById(R.id.activity_hello_continue_button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hello)

        val nameEditText: EditText = findViewById(R.id.activity_hello_editText_name)

        openHelloButton.setOnClickListener {
            saveUserName(nameEditText.text.toString())
            navigateToHelloScreen()
        }

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun afterTextChanged(str: Editable?) {
                val isInputEmpty: Boolean = str.toString().isBlank()
                openHelloButton.isEnabled = !isInputEmpty
            }
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
        val helloScreenIntent = Intent(this, TestHelloActivity::class.java)
        startActivity(helloScreenIntent)
    }
}