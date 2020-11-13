package kz.kolesateam.confapp.events.data.Models

data class EventApiData (
    val id: Int?,
    val startTime: String?,
    val endTime: String?,
    val title: String?,
    val description: String?,
    val place: String?,
    val speaker: SpeakerApiData?
)
