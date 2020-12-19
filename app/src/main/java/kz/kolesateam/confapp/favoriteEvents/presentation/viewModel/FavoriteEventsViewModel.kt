package kz.kolesateam.confapp.favoriteEvents.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.favoriteEvents.data.model.EventsState
import kz.kolesateam.confapp.common.domain.EventsMapper
import kz.kolesateam.confapp.favoriteEvents.domain.FavoriteEventsRepository

class FavoriteEventsViewModel(
    private val favoriteEventsRepository: FavoriteEventsRepository,
    private val eventsMapper: EventsMapper
) : ViewModel() {

    val favoriteEventsLiveData: MutableLiveData<List<EventApiData>> = MutableLiveData()
    val emptyEventsLiveData: MutableLiveData<EventsState> = MutableLiveData()

    fun onStart(){
        getFavoriteEvents()
    }

    fun onFavoriteButtonClick(eventApiData: EventApiData?) {
        when (eventApiData?.isFavorite) {
            true -> favoriteEventsRepository.removeEvent(eventId = eventApiData.id)
            else -> favoriteEventsRepository.saveEvent(event = eventApiData)
        }
    }

    private fun getFavoriteEvents(){
        val favoriteEventsList: List<EventApiData> = favoriteEventsRepository.getAllFavoriteEvents()

        if(favoriteEventsList.isEmpty()){
            emptyEventsLiveData.value = EventsState.Empty
        } else {
            setFavoriteEvents(favoriteEventsList)
            checkEventsTime(favoriteEventsList)

            favoriteEventsLiveData.value = favoriteEventsList
            emptyEventsLiveData.value = EventsState.Full
        }
    }

    private fun setFavoriteEvents(events: List<EventApiData>) = events.forEach { event ->
        event.isFavorite = eventsMapper.isFavoriteEvent(event)
    }

    private fun checkEventsTime(events: List<EventApiData>) = events.forEach {
        it.isCompleted = eventsMapper.isCompletedEvent(eventTime = it.endTime)
    }
}