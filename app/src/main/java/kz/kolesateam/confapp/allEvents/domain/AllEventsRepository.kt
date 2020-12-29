package kz.kolesateam.confapp.allEvents.domain

import androidx.annotation.WorkerThread
import kz.kolesateam.confapp.common.domain.models.EventData

interface AllEventsRepository {

    @WorkerThread
    suspend fun getAllEvents(
        result: (List<EventData>) -> Unit,
        fail: (String?) -> Unit,
        branchId: Int
    )
}