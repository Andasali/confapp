package kz.kolesateam.confapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kz.kolesateam.confapp.hello.presentation.HelloActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val openHelloButton: Button by lazy {
        findViewById(R.id.activity_main_open_hello_button)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openHelloButton.setOnClickListener {
            val helloScreenIntent = Intent(this, HelloActivity::class.java)
            startActivity(helloScreenIntent)
        }

        Log.d(TAG, "onCreate")
    }

    override fun onRestart() {
        super.onRestart()

        Log.d(TAG, "Inside onRestart")
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "Inside onStart")


    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        Log.d(TAG, "onPause")

        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")

        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")

        super.onDestroy()
    }
}