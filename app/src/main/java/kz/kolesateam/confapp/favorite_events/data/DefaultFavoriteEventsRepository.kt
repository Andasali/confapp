package kz.kolesateam.confapp.favorite_events.data

import kz.kolesateam.confapp.common.data.model.ResponseData
import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository

class DefaultFavoriteEventsRepository : FavoriteEventsRepository {

    private val favoriteEvents: MutableMap<Int, EventApiData> = mutableMapOf()

    override fun saveFavoriteEvent(eventApiData: EventApiData) {
        eventApiData.id ?: return

        favoriteEvents[eventApiData.id] = eventApiData
    }

    override fun removeFavoriteEvent(eventId: Int) {
        favoriteEvents.remove(eventId)
    }

    override fun getAllFavoriteEvents(): ResponseData<List<EventApiData>, Exception> {
        return ResponseData.Success(
            result = favoriteEvents.values.toList()
        )
    }
}