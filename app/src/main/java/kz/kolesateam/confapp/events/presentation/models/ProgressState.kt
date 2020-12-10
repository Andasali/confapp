package kz.kolesateam.confapp.events.presentation.models

sealed class ProgressState {
    object Loading : ProgressState()
    object Done : ProgressState()
}