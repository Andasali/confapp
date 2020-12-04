package kz.kolesateam.confapp.events.presentation.view

import android.widget.ImageView
import android.widget.ToggleButton

interface EventClickListener {
    fun onBranchClick(branchTitle: String)
    fun onEventClick(eventTitle: String)
}