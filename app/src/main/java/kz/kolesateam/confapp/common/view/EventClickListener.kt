package kz.kolesateam.confapp.common.view

import android.widget.ImageView
import kz.kolesateam.confapp.common.data.models.EventApiData

interface EventClickListener {

    fun onBranchClick(branchId: Int, branchTitle: String) = Unit
    fun onEventClick(eventTitle: String) = Unit
    fun onFavouriteButtonClick(image: ImageView, eventId: Int?, eventData: EventApiData) = Unit
}