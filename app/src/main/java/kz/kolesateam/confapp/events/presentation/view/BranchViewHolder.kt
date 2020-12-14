package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.models.BranchListItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.common.view.BaseViewHolder
import kz.kolesateam.confapp.common.view.EventClickListener
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.utils.extensions.show

const val TIME_AND_PLACE_FORMAT = "%s - %s • %s"
const val DROP_LAST_STRING_TIME = 3
const val DEFAULT_BRANCH_ID = 0
const val DEFAULT_BRANCH_TITLE = "Event"

class BranchViewHolder(
    itemView: View,
    private val eventClickListener: EventClickListener
) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val currentEventView: View = itemView.findViewById(R.id.events_card_current)
    private val nextEventView: View = itemView.findViewById(R.id.events_card_next)

    private val branchTitleAndArrow: View = itemView.findViewById(R.id.branch_item_title_and_arrow)
    private val branchTitle: TextView = itemView.findViewById(R.id.branch_item_branch_title)

    private val currentEventReportTextView: TextView = currentEventView.findViewById(R.id.events_card_layout_next_report)
    private val speakerNameCurrent: TextView = currentEventView.findViewById(R.id.events_card_speaker_name)
    private val eventCardAndPlaceCurrent: TextView = currentEventView.findViewById(R.id.events_card_date_and_place)
    private val speakerJobCurrent: TextView = currentEventView.findViewById(R.id.events_card_speaker_job)
    private val eventTitleCurrent: TextView = currentEventView.findViewById(R.id.events_card_event_title)
    private val favoriteButtonCurrent: ImageView = currentEventView.findViewById(R.id.events_card_favourite_btn)

    private val nextEventReportTextView: TextView = nextEventView.findViewById(R.id.events_card_layout_next_report)
    private val speakerNameNext: TextView = nextEventView.findViewById(R.id.events_card_speaker_name)
    private val eventCardAndPlaceNext: TextView = nextEventView.findViewById(R.id.events_card_date_and_place)
    private val speakerJobNext: TextView = nextEventView.findViewById(R.id.events_card_speaker_job)
    private val eventTitleNext: TextView = nextEventView.findViewById(R.id.events_card_event_title)
    private val favoriteButtonNext: ImageView = nextEventView.findViewById(R.id.events_card_favourite_btn)

    override fun onBind(data: UpcomingEventListItem) {
        val branchApiData = (data as? BranchListItem)?.data ?: return

        branchTitle.text = branchApiData.title

        nextEventReportTextView.show()
        nextEventView.setBackgroundResource(R.drawable.bg_events_card_dark)

        val currentEvent = branchApiData.events?.first()
        fillData(
            event = currentEvent,
            eventCardAndPlace = eventCardAndPlaceCurrent,
            speakerName = speakerNameCurrent,
            speakerJob = speakerJobCurrent,
            eventTitle = eventTitleCurrent,
            favoriteButton = favoriteButtonCurrent
        )

        val nextEvent = branchApiData.events?.last()
        fillData(
            event = nextEvent,
            eventCardAndPlace = eventCardAndPlaceNext,
            speakerName = speakerNameNext,
            speakerJob = speakerJobNext,
            eventTitle = eventTitleNext,
            favoriteButton = favoriteButtonNext
        )

        initListeners(
            branchApiData = branchApiData,
            currentEvent = currentEvent!!,
            nextEvent = nextEvent!!
        )
    }

    private fun fillData(
        event: EventApiData?,
        eventCardAndPlace: TextView,
        speakerName: TextView,
        speakerJob: TextView,
        eventTitle: TextView,
        favoriteButton: ImageView
    ) {
        eventCardAndPlace.text = TIME_AND_PLACE_FORMAT.format(
            event?.startTime?.dropLast(DROP_LAST_STRING_TIME),
            event?.endTime?.dropLast(DROP_LAST_STRING_TIME),
            event?.place
        )
        speakerName.text = event?.speaker?.fullName
        speakerJob.text = event?.speaker?.job
        eventTitle.text = event?.title

        val favoriteButtonResource = getFavoriteButtonResource(event?.isFavorite ?: false)
        favoriteButton.setImageResource(favoriteButtonResource)
    }

    private fun initListeners(
        branchApiData: BranchApiData,
        currentEvent: EventApiData,
        nextEvent: EventApiData
    ) {
        branchTitleAndArrow.setOnClickListener {
            eventClickListener.onBranchClicked(
                branchId = branchApiData.id ?: DEFAULT_BRANCH_ID,
                branchTitle = branchApiData.title ?: DEFAULT_BRANCH_TITLE
            )
        }

        currentEventView.setOnClickListener {
            eventClickListener.onEventClicked(currentEvent.title.toString())
        }

        nextEventView.setOnClickListener {
            eventClickListener.onEventClicked(nextEvent.title.toString())
        }

        favoriteButtonCurrent.setOnClickListener {
            val favoriteButtonResource = getFavoriteButtonResource(currentEvent.isFavorite)
            favoriteButtonCurrent.setImageResource(favoriteButtonResource)

            currentEvent.isFavorite = !currentEvent.isFavorite
            eventClickListener.onFavoriteButtonClicked(eventApiData = currentEvent)
        }

        favoriteButtonNext.setOnClickListener {
            val favoriteButtonResource = getFavoriteButtonResource(nextEvent.isFavorite)
            favoriteButtonNext.setImageResource(favoriteButtonResource)

            nextEvent.isFavorite = !nextEvent.isFavorite
            eventClickListener.onFavoriteButtonClicked(eventApiData = nextEvent)
        }
    }

    private fun getFavoriteButtonResource(isFavorite: Boolean): Int = when(isFavorite){
        true -> R.drawable.ic_favourite_fill
        else -> R.drawable.ic_favourite_border
    }
}