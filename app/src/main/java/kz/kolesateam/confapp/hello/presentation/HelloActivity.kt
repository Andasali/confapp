package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kz.kolesateam.confapp.APPLICATION_SHARED_PREFERENCES
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.USER_NAME_KEY

class HelloActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val nameText: TextView = findViewById(R.id.activity_hello_name_text)
        val userName: String = getSavedUserName()

        nameText.text = resources.getString(R.string.welcom, userName)

    }


    private fun getSavedUserName(): String {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
                APPLICATION_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(USER_NAME_KEY, "Hi") ?: "Hi"
    }

}