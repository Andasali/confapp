package kz.kolesateam.confapp.common.data

import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.common.domain.EventsMapper
import kz.kolesateam.confapp.favoriteEvents.domain.FavoriteEventsRepository
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

const val TIME_FORMAT = "HH:mm:ss"

class DefaultEventsMapper(
    private val favoriteEventsRepository: FavoriteEventsRepository
) : EventsMapper {

    override fun isFavoriteEvent(eventApiData: EventApiData?): Boolean {
        val favoriteEvents = favoriteEventsRepository.getAllFavoriteEvents()

        return favoriteEvents.contains(eventApiData)
    }

    override fun isCompletedEvent(eventTime: String?): Boolean {
        val simpleDateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        val currentTime: Date = simpleDateFormat.parse(simpleDateFormat.format(Date())) ?: Date()
        val eventEndTime: Date = simpleDateFormat.parse(eventTime) ?: Date()

        if (currentTime.after(eventEndTime)) {
            return true
        }

        return false
    }
}