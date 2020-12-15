package kz.kolesateam.confapp.hello.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.AbstractTextWatcher
import kz.kolesateam.confapp.events.domain.UserNameDataSource
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import org.koin.android.ext.android.inject

class HelloActivity : AppCompatActivity() {

    private val userNameSharedPrefsDataSource: UserNameDataSource by inject()

    private lateinit var editText: EditText
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        initViews()
    }

    private fun initViews(){
        editText = findViewById(R.id.activity_hello_editText_name)
        continueButton = findViewById(R.id.activity_hello_continue_button)

        editText.addTextChangedListener(object: AbstractTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                continueButton.isEnabled = s.toString().isNotBlank()
            }
        })

        continueButton.setOnClickListener {
            saveName(editText.text.toString())
            startActivity(Intent(this, UpcomingEventsActivity::class.java))
        }
    }

    private fun saveName(name: String) = userNameSharedPrefsDataSource.saveUserName(name)
}