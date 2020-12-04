package kz.kolesateam.confapp.common.data.model

sealed class EventScreenNavigation {
    data class AllEvents(
        val branchId: Int,
        val branchTitle: String
    ) : EventScreenNavigation()
}