package kz.kolesateam.confapp.common.domain

import kz.kolesateam.confapp.common.data.models.EventApiData

interface EventsMapper {

    fun isFavoriteEvent(eventApiData: EventApiData?): Boolean

    fun isCompletedEvent(eventTime: String?): Boolean
}