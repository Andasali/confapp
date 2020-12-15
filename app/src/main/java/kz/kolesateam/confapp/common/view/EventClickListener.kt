package kz.kolesateam.confapp.common.view

import android.widget.ImageView

interface EventClickListener {

    fun onBranchClick(branchId: Int, branchTitle: String) = Unit
    fun onEventClick(eventTitle: String) = Unit
    fun onFavouriteButtonClick(image: ImageView, eventId: Int?) = Unit
}