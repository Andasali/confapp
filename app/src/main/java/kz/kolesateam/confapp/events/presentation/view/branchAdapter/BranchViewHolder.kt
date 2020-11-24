package kz.kolesateam.confapp.events.presentation.view.branchAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.view.EventClickListener

class BranchViewHolder(
    itemView: View,
    private val eventClickListener: EventClickListener,
) : RecyclerView.ViewHolder(itemView) {

    private val currentEventView: View = itemView.findViewById(R.id.events_card_current)
    private val nextEventView: View = itemView.findViewById(R.id.events_card_next)

    private val branchTitleAndArrow: View = itemView.findViewById(R.id.branch_item_title_and_arrow)
    private val branchTitle: TextView = itemView.findViewById(R.id.branch_item_branch_title)

    private val nextEventReportTextView: TextView = currentEventView.findViewById(R.id.events_card_layout_next_report)
    private val speakerNameCurrent: TextView = currentEventView.findViewById(R.id.events_card_speaker_name)
    private val eventCardAndPlaceCurrent: TextView = currentEventView.findViewById(R.id.events_card_date_and_place)
    private val speakerJobCurrent: TextView = currentEventView.findViewById(R.id.events_card_speaker_job)
    private val eventTitleCurrent: TextView = currentEventView.findViewById(R.id.events_card_event_title)
    private val favouriteButtonCurrent: ImageView = currentEventView.findViewById(R.id.events_card_favourite_btn)

    private val speakerNameNext: TextView = nextEventView.findViewById(R.id.events_card_speaker_name)
    private val eventCardAndPlaceNext: TextView = nextEventView.findViewById(R.id.events_card_date_and_place)
    private val speakerJobNext: TextView = nextEventView.findViewById(R.id.events_card_speaker_job)
    private val eventTitleNext: TextView = nextEventView.findViewById(R.id.events_card_event_title)
    private val favouriteButtonNext: ImageView = nextEventView.findViewById(R.id.events_card_favourite_btn)

    fun bind(branchApiData: BranchApiData){
        branchTitle.text = branchApiData.title

        val currentEvent = branchApiData.events?.first()
        fillData(
            currentEvent,
            eventCardAndPlaceCurrent,
            speakerNameCurrent,
            speakerJobCurrent,
            eventTitleCurrent
        )

        val nextEvent = branchApiData.events?.last()
        fillData(
            nextEvent,
            eventCardAndPlaceNext,
            speakerNameNext,
            speakerJobNext,
            eventTitleNext
        )

        initListeners(currentEvent, nextEvent)
    }

    private fun fillData(
        event: EventApiData?,
        eventCardAndPlace: TextView,
        speakerName: TextView,
        speakerJob: TextView,
        eventTitle: TextView
    ){
        eventCardAndPlace.text = "%s - %s • %s".format(
            event?.startTime?.dropLast(3),
            event?.endTime?.dropLast(3),
            event?.place
        )
        speakerName.text = event?.speaker?.fullName
        speakerJob.text = event?.speaker?.job
        eventTitle.text = event?.title
    }

    private fun initListeners(currentEvent: EventApiData?, nextEvent: EventApiData?){
        branchTitleAndArrow.setOnClickListener {
            eventClickListener.onBranchClickListener(branchTitle.text.toString())
        }

        currentEventView.setOnClickListener {
            eventClickListener.onEventClickListener(currentEvent?.title.toString())
        }

        nextEventView.setOnClickListener {
            eventClickListener.onEventClickListener(nextEvent?.title.toString())
        }

        favouriteButtonCurrent.setOnClickListener {
            eventClickListener.onFavouriteButtonClickListener(favouriteButtonCurrent, currentEvent?.id)
        }

        favouriteButtonNext.setOnClickListener {
            eventClickListener.onFavouriteButtonClickListener(favouriteButtonNext, nextEvent?.id)
        }
    }
}