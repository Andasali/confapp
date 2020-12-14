package kz.kolesateam.confapp.allEvents.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.kolesateam.confapp.allEvents.domain.AllEventsRepository
import kz.kolesateam.confapp.allEvents.presentation.models.AllEventsListItem
import kz.kolesateam.confapp.allEvents.presentation.models.BranchTitleItem
import kz.kolesateam.confapp.allEvents.presentation.models.EventListItem
import kz.kolesateam.confapp.common.data.model.ProgressState
import kz.kolesateam.confapp.common.data.model.ResponseData
import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.view.DEFAULT_BRANCH_ID
import kz.kolesateam.confapp.events.presentation.view.DEFAULT_BRANCH_TITLE
import kz.kolesateam.confapp.common.domain.EventsMapper
import kz.kolesateam.confapp.favoriteEvents.domain.FavoriteEventsRepository

class AllEventsViewModel(
    private val allEventsRepository: AllEventsRepository,
    private val favoriteEventsRepository: FavoriteEventsRepository,
    private val eventsMapper: EventsMapper
) : ViewModel() {

    val allEventsLiveData: MutableLiveData<ResponseData<List<AllEventsListItem>, String>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    private val branchIdLiveData: MutableLiveData<Int> = MutableLiveData()
    private val branchTitleLiveData: MutableLiveData<String> = MutableLiveData()

    fun onStart(branchId: Int, branchTitle: String) {
        branchIdLiveData.value = branchId
        branchTitleLiveData.value = branchTitle

        getAllEvents()
    }

    fun onFavoriteButtonClick(eventApiData: EventApiData?) {
        when (eventApiData?.isFavorite) {
            true -> favoriteEventsRepository.removeEvent(eventId = eventApiData.id)
            else -> favoriteEventsRepository.saveEvent(event = eventApiData)
        }
    }

    private fun getAllEvents() = viewModelScope.launch {
        progressLiveData.value = ProgressState.Loading

        allEventsRepository.getAllEvents(
            result = {
                setFavoriteEvents(events = it)
                checkEventsTime(events = it)

                allEventsLiveData.value = ResponseData.Success(prepareAllEvents(it))
            },
            fail = {
                allEventsLiveData.value = ResponseData.Error(it.toString())
            },
            branchId = branchIdLiveData.value ?: DEFAULT_BRANCH_ID
        )

        progressLiveData.value = ProgressState.Done
    }

    private fun setFavoriteEvents(events: List<EventApiData>) = events.forEach { event ->
        event.isFavorite = eventsMapper.isFavoriteEvent(event)
    }

    private fun prepareAllEvents(
        eventsList: List<EventApiData>
    ): List<AllEventsListItem> = listOf(getBranchTitleItem()) + getEventsItem(eventsList)

    private fun getBranchTitleItem(): AllEventsListItem {
        return BranchTitleItem(
            branchTitle = branchTitleLiveData.value ?: DEFAULT_BRANCH_TITLE
        )
    }

    private fun getEventsItem(events: List<EventApiData>): List<AllEventsListItem> = events.map {
        EventListItem(data = it)
    }

    private fun checkEventsTime(events: List<EventApiData>) = events.forEach {
        it.isCompleted = eventsMapper.isCompletedEvent(eventTime = it.endTime)
    }
}