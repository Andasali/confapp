package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.common.data.models.SpeakerApiData
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject

const val ID_KEY = "id"
const val TITLE_KEY = "title"
const val EVENTS_KEY = "events"
const val START_TIME_KEY = "startTime"
const val END_TIME_KEY = "endTime"
const val DESCRIPTION_KEY = "description"
const val PLACE_KEY = "place"
const val SPEAKER_KEY = "speaker"
const val FULL_NAME_KEY = "fullName"
const val JOB_KEY = "job"
const val PHOTO_URL_KEY = "photoUrl"

class BranchMapper {
    fun parseBranchList(responseBody: ResponseBody): List<BranchApiData> {
        val branchJsonArray = JSONArray(responseBody.string())

        return parseBranchFromJsonArray(branchJsonArray)
    }

    private fun parseBranchFromJsonArray(branchJsonArray: JSONArray): List<BranchApiData> {
        val branchList = mutableListOf<BranchApiData>()

        for (i in 0 until branchJsonArray.length()) {
            val branch = branchJsonArray.getJSONObject(i)

            val id = branch.getInt(ID_KEY)
            val title = branch.getString(TITLE_KEY)
            val events: List<EventApiData> =
                parseEventsFromJsonArray(branch.getJSONArray(EVENTS_KEY))

            branchList.add(BranchApiData(id, title, events))
        }

        return branchList
    }

    private fun parseEventsFromJsonArray(eventsJsonArray: JSONArray): List<EventApiData> {
        val eventsList = mutableListOf<EventApiData>()

        for (i in 0 until eventsJsonArray.length()) {
            val event = eventsJsonArray.getJSONObject(i)

            val id = event.getInt(ID_KEY)
            val startTime = event.getString(START_TIME_KEY)
            val endTime = event.getString(END_TIME_KEY)
            val title = event.getString(TITLE_KEY)
            val description = event.getString(DESCRIPTION_KEY)
            val place = event.getString(PLACE_KEY)
            val speaker: SpeakerApiData =
                parseSpeakerFromJsonObject(event.getJSONObject(SPEAKER_KEY))

            eventsList.add(
                EventApiData(id, startTime, endTime, title, description, place, speaker)
            )
        }

        return eventsList
    }

    private fun parseSpeakerFromJsonObject(speakerJsonObject: JSONObject): SpeakerApiData {
        val id = speakerJsonObject.getInt(ID_KEY)
        val fullName = speakerJsonObject.getString(FULL_NAME_KEY)
        val job = speakerJsonObject.getString(JOB_KEY)
        val photoUrl = speakerJsonObject.getString(PHOTO_URL_KEY)

        return SpeakerApiData(id, fullName, job, photoUrl)
    }
}