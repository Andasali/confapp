package kz.kolesateam.confapp.allEvents.data

import kz.kolesateam.confapp.allEvents.domain.AllEventsRepository
import kz.kolesateam.confapp.common.data.models.EventApiData
import retrofit2.awaitResponse

class DefaultAllEventsRepository(
    private val allEventsDataSource: AllEventsDataSource
) : AllEventsRepository {

    override suspend fun getAllEvents(
        result: (List<EventApiData>) -> Unit,
        fail: (String?) -> Unit,
        branchId: Int
    ) {
        try {
            val response = allEventsDataSource.getAllEvents(branchId).awaitResponse()

            if(response.isSuccessful){
                result(response.body()!!)
            } else {
                fail(response.message())
            }
        } catch (e: Exception){
            fail(e.localizedMessage)
        }
    }
}