package kz.kolesateam.confapp.favorite_events.domain

import kz.kolesateam.confapp.common.data.model.ResponseData
import kz.kolesateam.confapp.common.data.models.EventApiData

interface FavoriteEventsRepository {

    fun saveFavoriteEvent(
        eventApiData: EventApiData
    )

    fun removeFavoriteEvent(
        eventId: Int
    )

    fun getAllFavoriteEvents(): ResponseData<List<EventApiData>, Exception>
}