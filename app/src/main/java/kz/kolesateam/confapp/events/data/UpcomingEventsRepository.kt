package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.Models.BranchApiData
import kz.kolesateam.confapp.events.data.Models.EventApiData
import kz.kolesateam.confapp.events.data.Models.SpeakerApiData
import kz.kolesateam.confapp.network.apiClient
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

class UpcomingEventsRepository {
    fun getUpcomingEventsSync(): ResponseData<List<BranchApiData>, String> {
        return try {
            val response: Response<ResponseBody> = apiClient.getUpcomingEvents().execute()

            if (response.isSuccessful) {
                val responseBody: ResponseBody = response.body()!!
                val branchJsonArray = JSONArray(responseBody.string())
                val branchList = parseBranchFromJsonArray(branchJsonArray)

                ResponseData.Success(branchList)
            } else {
                ResponseData.Error("Error occured")
            }
        } catch (e: Exception) {
            ResponseData.Error(e.localizedMessage)
        }
    }

    private fun parseBranchFromJsonArray(branchJsonArray: JSONArray): List<BranchApiData> {
        val branchList = mutableListOf<BranchApiData>()

        for (i in 0 until branchJsonArray.length()) {
            val branch = branchJsonArray.getJSONObject(i)
            val id = branch.getInt("id")
            val title = branch.getString("title")
            val events: List<EventApiData> = parseEventsFromJsonArray(branch.getJSONArray("events"))

            branchList.add(BranchApiData(id, title, events))
        }

        return branchList
    }

    private fun parseEventsFromJsonArray(eventsJsonArray: JSONArray): List<EventApiData> {
        val eventsList = mutableListOf<EventApiData>()

        for (i in 0 until eventsJsonArray.length()) {
            val event = eventsJsonArray.getJSONObject(i)
            val id = event.getInt("id")
            val startTime = event.getString("startTime")
            val endTime = event.getString("endTime")
            val title = event.getString("title")
            val description = event.getString("description")
            val place = event.getString("place")
            val speaker: SpeakerApiData = parseSpeakerFromJsonObject(event.getJSONObject("speaker"))

            eventsList.add(
                EventApiData(id, startTime, endTime, title, description, place, speaker)
            )
        }

        return eventsList
    }

    private fun parseSpeakerFromJsonObject(speakerJsonObject: JSONObject): SpeakerApiData {
        val id = speakerJsonObject.getInt("id")
        val fullName = speakerJsonObject.getString("fullName")
        val job = speakerJsonObject.getString("job")
        val photoUrl = speakerJsonObject.getString("photoUrl")

        return SpeakerApiData(id, fullName, job, photoUrl)
    }
}