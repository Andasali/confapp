package kz.kolesateam.confapp.favoriteEvents.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.common.view.EventClickListener
import kz.kolesateam.confapp.events.data.EMPTY_KEY
import kz.kolesateam.confapp.events.presentation.view.DROP_LAST_STRING_TIME
import kz.kolesateam.confapp.events.presentation.view.TIME_AND_PLACE_FORMAT
import kz.kolesateam.confapp.utils.extensions.show

class FavoriteEventViewHolder(
    itemView: View,
    private val eventClickListener: EventClickListener
): RecyclerView.ViewHolder(itemView) {

    private val completedStateTextView: TextView = itemView.findViewById(R.id.events_card_layout_completed)
    private val dateAndPlaceTextView: TextView = itemView.findViewById(R.id.events_card_date_and_place)
    private val favoriteButton: ImageView = itemView.findViewById(R.id.events_card_favourite_btn)
    private val speakerNameTextView: TextView = itemView.findViewById(R.id.events_card_speaker_name)
    private val speakerJobTextView: TextView = itemView.findViewById(R.id.events_card_speaker_job)
    private val eventTitleTextView: TextView = itemView.findViewById(R.id.events_card_event_title)


    fun onBind(event: EventApiData){
        fillData(event)
        initListeners(event)

        if(event.isCompleted){
            onEventComplete()
        }
    }

    private fun fillData(event: EventApiData?){
        dateAndPlaceTextView.text = TIME_AND_PLACE_FORMAT.format(
            event?.startTime?.dropLast(DROP_LAST_STRING_TIME),
            event?.endTime?.dropLast(DROP_LAST_STRING_TIME),
            event?.place
        )
        speakerNameTextView.text = event?.speaker?.fullName
        speakerJobTextView.text = event?.speaker?.job
        eventTitleTextView.text = event?.title

        val favoriteButtonResource = getFavoriteButtonResource(event?.isFavorite ?: false)
        favoriteButton.setImageResource(favoriteButtonResource)
    }

    private fun initListeners(event: EventApiData) {
        itemView.setOnClickListener {
            eventClickListener.onEventClicked(eventTitle = event.title ?: EMPTY_KEY)
        }

        favoriteButton.setOnClickListener {
            val favoriteButtonResource = getFavoriteButtonResource(event.isFavorite)
            favoriteButton.setImageResource(favoriteButtonResource)

            event.isFavorite = !event.isFavorite
            eventClickListener.onFavoriteButtonClicked(eventApiData = event)
        }
    }

    private fun onEventComplete(){
        completedStateTextView.show()
        itemView.setBackgroundResource(R.drawable.bg_events_card_dark)
    }

    private fun getFavoriteButtonResource(isFavorite: Boolean): Int = when(isFavorite){
        true -> R.drawable.ic_favourite_fill
        else -> R.drawable.ic_favourite_border
    }
}