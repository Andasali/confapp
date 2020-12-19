package kz.kolesateam.confapp.favoriteEvents.data

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.MapType
import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.favoriteEvents.domain.FavoriteEventsRepository
import java.io.FileInputStream
import java.io.FileOutputStream

const val FAVOURITE_EVENTS_FILE_NAME = "favourite_events.json"

class DefaultFavoriteEventsRepository(
    private val context: Context,
    private val objectMapper: ObjectMapper
) : FavoriteEventsRepository {

    private var favoriteEventsMap: MutableMap<Int, EventApiData> = mutableMapOf()

    init {
        favoriteEventsMap.putAll(getFavoriteEventsFromFile())
    }

    override fun saveEvent(event: EventApiData?) {
        event?.id ?: return

        favoriteEventsMap[event.id] = event
        saveFavoriteEventsToFile()
    }

    override fun removeEvent(eventId: Int?) {
        favoriteEventsMap.remove(eventId)
        saveFavoriteEventsToFile()
    }

    override fun getAllFavoriteEvents(): List<EventApiData> {
        return favoriteEventsMap.values.toList()
    }

    private fun saveFavoriteEventsToFile() {
        val favoriteEventsJsonString: String = objectMapper.writeValueAsString(favoriteEventsMap)
        val fileOutputStream: FileOutputStream = context.openFileOutput(
            FAVOURITE_EVENTS_FILE_NAME,
            Context.MODE_PRIVATE
        )
        fileOutputStream.write(favoriteEventsJsonString.toByteArray())
        fileOutputStream.close()
    }

    private fun getFavoriteEventsFromFile(): Map<Int, EventApiData> {
        var fileInputStream: FileInputStream? = null

        try {
            fileInputStream = context.openFileInput(FAVOURITE_EVENTS_FILE_NAME)
        } catch (e: Exception) {
            fileInputStream?.close()

            return emptyMap()
        }

        val favoriteEventsJsonString: String = fileInputStream.bufferedReader().readLines().joinToString()
        val mapType: MapType = objectMapper.typeFactory.constructMapType(Map::class.java, Int::class.java, EventApiData::class.java)

        return objectMapper.readValue(favoriteEventsJsonString, mapType)
    }
}