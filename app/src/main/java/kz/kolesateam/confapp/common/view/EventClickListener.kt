package kz.kolesateam.confapp.common.view

import kz.kolesateam.confapp.common.data.models.EventApiData

interface EventClickListener {

    fun onBranchClicked(
        branchId: Int,
        branchTitle: String
    ) = Unit

    fun onEventClicked(eventTitle: String) = Unit

    fun onFavoriteButtonClicked(eventApiData: EventApiData) = Unit
}