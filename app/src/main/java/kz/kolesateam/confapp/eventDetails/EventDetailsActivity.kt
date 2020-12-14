package kz.kolesateam.confapp.eventDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.notifications.NOTIFICATION_EVENT_TITLE_KEY

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var contentTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        initViews()
    }

    private fun initViews() {
        contentTextView = findViewById(R.id.activity_event_details_text_view)
        contentTextView.text = intent.getStringExtra(NOTIFICATION_EVENT_TITLE_KEY)
    }
}