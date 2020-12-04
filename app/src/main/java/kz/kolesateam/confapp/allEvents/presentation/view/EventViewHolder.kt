package kz.kolesateam.confapp.allEvents.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allEvents.presentation.models.AllEventsListItem
import kz.kolesateam.confapp.allEvents.presentation.models.EventListItem
import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.common.view.BaseViewHolder
import kz.kolesateam.confapp.common.view.EventClickListener
import kz.kolesateam.confapp.events.data.EMPTY_KEY
import kz.kolesateam.confapp.events.presentation.view.DROP_LAST_STRING_TIME
import kz.kolesateam.confapp.events.presentation.view.TIME_AND_PLACE_FORMAT
import kz.kolesateam.confapp.utils.show

class EventViewHolder(
    itemView: View,
    private val eventClickListener: EventClickListener
) : BaseViewHolder<AllEventsListItem>(itemView) {

    private val completedStateTextView: TextView = itemView.findViewById(R.id.events_card_layout_completed)
    private val dateAndPlaceTextView: TextView = itemView.findViewById(R.id.events_card_date_and_place)
    private val favoriteButton: ImageView = itemView.findViewById(R.id.events_card_favourite_btn)
    private val speakerNameTextView: TextView = itemView.findViewById(R.id.events_card_speaker_name)
    private val speakerJobTextView: TextView = itemView.findViewById(R.id.events_card_speaker_job)
    private val eventTitleTextView: TextView = itemView.findViewById(R.id.events_card_event_title)

    override fun onBind(data: AllEventsListItem) {
        val event = (data as? EventListItem)?.data
        val isCompleted = (data as? EventListItem)?.isCompleted ?: false

        fillData(event)
        initListeners(event)

        if(isCompleted){
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
    }

    private fun initListeners(event: EventApiData?) {
        itemView.setOnClickListener {
            eventClickListener.onEventClick(eventTitle = event?.title ?: EMPTY_KEY)
        }

        favoriteButton.setOnClickListener {
            eventClickListener.onFavouriteButtonClick(
                image = favoriteButton,
                eventId = event?.id
            )
        }
    }

    private fun onEventComplete(){
        completedStateTextView.show()
        itemView.setBackgroundResource(R.drawable.bg_events_card_dark)
    }
}