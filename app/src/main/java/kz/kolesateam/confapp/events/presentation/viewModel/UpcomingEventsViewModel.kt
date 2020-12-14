package kz.kolesateam.confapp.events.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.common.data.model.EventScreenNavigation
import kz.kolesateam.confapp.common.data.model.ProgressState
import kz.kolesateam.confapp.common.data.model.ResponseData
import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.events.domain.UserNameDataSource
import kz.kolesateam.confapp.events.presentation.models.BranchListItem
import kz.kolesateam.confapp.events.presentation.models.HeaderItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.common.domain.EventsMapper
import kz.kolesateam.confapp.favoriteEvents.domain.FavoriteEventsRepository

class UpcomingEventsViewModel(
    private val upcomingEventsRepository: UpcomingEventsRepository,
    private val userNameSharedPrefsDataSource: UserNameDataSource,
    private val favoriteEventsRepository: FavoriteEventsRepository,
    private val eventsMapper: EventsMapper
) : ViewModel() {

    val loadEventsStateLiveData: MutableLiveData<ResponseData<List<UpcomingEventListItem>, String>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    val eventScreenNavigationLiveData: MutableLiveData<EventScreenNavigation> = MutableLiveData()

    fun onStart() {
        progressLiveData.value = ProgressState.Loading

        upcomingEventsRepository.getUpcomingEvents(
            result = {
                loadEventsStateLiveData.value = ResponseData.Success(prepareAdapterList(it))
                progressLiveData.value = ProgressState.Done
            },
            fail = {
                loadEventsStateLiveData.value = ResponseData.Error(it.toString())
                progressLiveData.value = ProgressState.Done
            }
        )
    }

    fun onFavoriteButtonClick(eventApiData: EventApiData?) {
        when(eventApiData?.isFavorite){
            true -> favoriteEventsRepository.removeEvent(eventId = eventApiData.id)
            else -> favoriteEventsRepository.saveEvent(event = eventApiData)
        }
    }

    fun onBranchClickListener(eventScreenNavigation: EventScreenNavigation) {
        eventScreenNavigationLiveData.value = eventScreenNavigation
    }

    private fun prepareAdapterList(branchList: List<BranchApiData>): List<UpcomingEventListItem> {
        return listOf(getHeaderItem()) + getBranchItems(branchList)
    }

    private fun getHeaderItem(): UpcomingEventListItem = HeaderItem(
        userName = userNameSharedPrefsDataSource.getUserName()
    )

    private fun getBranchItems(branchList: List<BranchApiData>): List<UpcomingEventListItem> = branchList.map { branchApiData ->
        branchApiData.events?.forEach {
            it.isFavorite = eventsMapper.isFavoriteEvent(it)
        }

        BranchListItem(data = branchApiData)
    }
}