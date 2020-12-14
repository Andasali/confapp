package kz.kolesateam.confapp.favoriteEvents.domain

import kz.kolesateam.confapp.common.data.models.EventApiData

interface FavoriteEventsRepository {

    fun saveEvent(event: EventApiData?)

    fun removeEvent(eventId: Int?)

    fun getAllFavoriteEvents(): List<EventApiData>
}