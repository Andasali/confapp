package kz.kolesateam.confapp.eventDetails.data

import android.widget.ImageView
import com.bumptech.glide.Glide
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.eventDetails.domain.ImageLoader

class DefaultImageLoader : ImageLoader {

    override fun load(
        url: String,
        target: ImageView
    ) {
        Glide.with(target.context)
            .load(url)
            .placeholder(R.drawable.ic_corona)
            .into(target)
    }
}