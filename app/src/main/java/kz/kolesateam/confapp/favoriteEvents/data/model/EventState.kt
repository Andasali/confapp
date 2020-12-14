package kz.kolesateam.confapp.favoriteEvents.data.model

sealed class EventsState {
    object Empty : EventsState()
    object Full : EventsState()
}