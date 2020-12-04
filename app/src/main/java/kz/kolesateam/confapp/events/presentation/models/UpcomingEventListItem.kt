package kz.kolesateam.confapp.events.presentation.models

import kz.kolesateam.confapp.events.data.models.BranchApiData

const val HEADER_TYPE = 0
const val EVENT_TYPE = 1

sealed class UpcomingEventListItem (
    val type: Int
)

data class HeaderItem(
    val userName: String
): UpcomingEventListItem(HEADER_TYPE)

data class BranchListItem(
    val data: BranchApiData
): UpcomingEventListItem(EVENT_TYPE)