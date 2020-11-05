package kz.kolesateam.confapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kz.kolesateam.confapp.hello.presentation.HelloActivity

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