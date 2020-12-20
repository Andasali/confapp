package kz.kolesateam.confapp.eventDetails.domain

import android.widget.ImageView

interface ImageLoader {

    fun load(
        url: String,
        target: ImageView
    )
}