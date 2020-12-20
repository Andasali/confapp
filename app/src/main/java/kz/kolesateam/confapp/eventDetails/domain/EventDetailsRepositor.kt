package kz.kolesateam.confapp.eventDetails.domain

import androidx.annotation.WorkerThread
import kz.kolesateam.confapp.common.domain.models.EventData

interface EventDetailsRepository {

    @WorkerThread
    suspend fun getEventDetails(
        result: (EventData) -> Unit,
        fail: (String?) -> Unit,
        eventId: Int
    )
}