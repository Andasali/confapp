package kz.kolesateam.confapp.events.presentation.view

import android.widget.ImageView

interface EventClickListener {
    fun onBranchClickListener(branchTitle: String)
    fun onEventClickListener(eventTitle: String)
    fun onFavouriteButtonClickListener(image: ImageView, eventId: Int?)
}