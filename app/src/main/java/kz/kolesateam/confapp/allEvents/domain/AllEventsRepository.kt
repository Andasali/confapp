package kz.kolesateam.confapp.allEvents.domain

import androidx.annotation.WorkerThread
import kz.kolesateam.confapp.common.data.models.EventApiData

interface AllEventsRepository {

    @WorkerThread
    suspend fun getAllEvents(
        result: (List<EventApiData>) -> Unit,
        fail: (String?) -> Unit,
        branchId: Int
    )
}