package kz.kolesateam.confapp.events.data.Models

data class BranchApiData (
    val id: Int?,
    val title: String?,
    val events: List<EventApiData>?
    )