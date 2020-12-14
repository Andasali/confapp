package kz.kolesateam.confapp.allEvents.presentation.models

import kz.kolesateam.confapp.common.data.models.EventApiData

const val BRANCH_TITLE_TYPE = 0
const val EVENT_TYPE = 1

sealed class AllEventsListItem(
    val type: Int
)

data class BranchTitleItem(
    val branchTitle: String
) : AllEventsListItem(BRANCH_TITLE_TYPE)

data class EventListItem(
    val data: EventApiData
) : AllEventsListItem(EVENT_TYPE)