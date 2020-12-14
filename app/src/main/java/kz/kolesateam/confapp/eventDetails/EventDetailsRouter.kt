package kz.kolesateam.confapp.eventDetails

import android.content.Context
import android.content.Intent

class EventDetailsRouter {

    fun createIntent(
        context: Context
    ): Intent = Intent(context, EventDetailsActivity::class.java)
}