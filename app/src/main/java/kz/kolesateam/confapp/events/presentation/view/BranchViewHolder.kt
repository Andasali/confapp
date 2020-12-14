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

    private lateinit var currentEvent: EventApiData
    private lateinit var nextEvent: EventApiData

    private val currentEventView: View = itemView.findViewById(R.id.events_card_current)
    private val nextEventView: View = itemView.findViewById(R.id.events_card_next)

    private val branchTitleAndArrow: View = itemView.findViewById(R.id.branch_item_title_and_arrow)
    private val branchTitle: TextView = itemView.findViewById(R.id.branch_item_branch_title)

    private val currentEventReportTextView: TextView =
        currentEventView.findViewById(R.id.events_card_layout_next_report)
    private val speakerNameCurrent: TextView =
        currentEventView.findViewById(R.id.events_card_speaker_name)
    private val eventCardAndPlaceCurrent: TextView =
        currentEventView.findViewById(R.id.events_card_date_and_place)
    private val speakerJobCurrent: TextView =
        currentEventView.findViewById(R.id.events_card_speaker_job)
    private val eventTitleCurrent: TextView =
        currentEventView.findViewById(R.id.events_card_event_title)
    private val favouriteButtonCurrent: ImageView =
        currentEventView.findViewById(R.id.events_card_favourite_btn)

    private val nextEventReportTextView: TextView =
        nextEventView.findViewById(R.id.events_card_layout_next_report)
    private val speakerNameNext: TextView =
        nextEventView.findViewById(R.id.events_card_speaker_name)
    private val eventCardAndPlaceNext: TextView =
        nextEventView.findViewById(R.id.events_card_date_and_place)
    private val speakerJobNext: TextView = nextEventView.findViewById(R.id.events_card_speaker_job)
    private val eventTitleNext: TextView = nextEventView.findViewById(R.id.events_card_event_title)
    private val favouriteButtonNext: ImageView =
        nextEventView.findViewById(R.id.events_card_favourite_btn)

    override fun onBind(data: UpcomingEventListItem) {
        val branchApiData = (data as? BranchListItem)?.data ?: return

        branchTitle.text = branchApiData.title

        nextEventReportTextView.show()
        nextEventView.setBackgroundResource(R.drawable.bg_events_card_dark)

        if (branchApiData.events.isEmpty()) {
            currentEventView.visibility = View.GONE
            nextEventView.visibility = View.GONE
        }

        currentEvent = branchApiData.events.first()
        fillData(
            event = currentEvent,
            eventCardAndPlace = eventCardAndPlaceCurrent,
            speakerName = speakerNameCurrent,
            speakerJob = speakerJobCurrent,
            eventTitle = eventTitleCurrent
        )

        nextEvent = branchApiData.events.last()
        fillData(
            event = nextEvent,
            eventCardAndPlace = eventCardAndPlaceNext,
            speakerName = speakerNameNext,
            speakerJob = speakerJobNext,
            eventTitle = eventTitleNext
        )

        initListeners(
            branchApiData = branchApiData,
            currentEvent = currentEvent,
            nextEvent = nextEvent
        )
    }

    private fun fillData(
        event: EventApiData?,
        eventCardAndPlace: TextView,
        speakerName: TextView,
        speakerJob: TextView,
        eventTitle: TextView
    ) {
        eventCardAndPlace.text = TIME_AND_PLACE_FORMAT.format(
            event?.startTime?.dropLast(DROP_LAST_STRING_TIME),
            event?.endTime?.dropLast(DROP_LAST_STRING_TIME),
            event?.place
        )
        speakerName.text = event?.speaker?.fullName
        speakerJob.text = event?.speaker?.job
        eventTitle.text = event?.title
    }

    private fun initListeners(
        branchApiData: BranchApiData,
        currentEvent: EventApiData?,
        nextEvent: EventApiData?
    ) {
        branchTitleAndArrow.setOnClickListener {
            eventClickListener.onBranchClick(
                branchId = branchApiData.id ?: DEFAULT_BRANCH_ID,
                branchTitle = branchApiData.title ?: DEFAULT_BRANCH_TITLE
            )
        }

        currentEventView.setOnClickListener {
            eventClickListener.onEventClick(currentEvent?.title.toString())
        }

        nextEventView.setOnClickListener {
            eventClickListener.onEventClick(nextEvent?.title.toString())
        }

        favouriteButtonCurrent.setOnClickListener {
            this.currentEvent.isFavorite = !this.currentEvent.isFavorite
            val favoriteImageResource = when (this.currentEvent.isFavorite) {
                true -> R.drawable.ic_favourite_fill
                else -> R.drawable.ic_favourite_border
            }
            favouriteButtonCurrent.setImageResource(favoriteImageResource)
            eventClickListener.onFavoriteButtonClick(
                favouriteButtonCurrent,
                currentEvent?.id,
                this.currentEvent
            )
        }

        favouriteButtonNext.setOnClickListener {
            this.nextEvent.isFavorite = !this.nextEvent.isFavorite
            val favoriteImageResource = when (this.nextEvent.isFavorite) {
                true -> R.drawable.ic_favourite_fill
                else -> R.drawable.ic_favourite_border
            }
            favouriteButtonNext.setImageResource(favoriteImageResource)
            eventClickListener.onFavoriteButtonClick(
                favouriteButtonNext,
                nextEvent?.id,
                this.nextEvent
            )
        }
    }
}