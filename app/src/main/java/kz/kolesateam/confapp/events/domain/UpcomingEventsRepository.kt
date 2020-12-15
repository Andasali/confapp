package kz.kolesateam.confapp.events.domain

import androidx.annotation.WorkerThread
import kz.kolesateam.confapp.events.data.models.BranchApiData

interface UpcomingEventsRepository {

    @WorkerThread
    fun getUpcomingEvents(
        result: (List<BranchApiData>) -> Unit,
        fail: (String?) -> Unit
    )
}