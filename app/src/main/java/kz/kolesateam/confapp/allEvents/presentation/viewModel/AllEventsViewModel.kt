package kz.kolesateam.confapp.allEvents.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.kolesateam.confapp.allEvents.domain.AllEventsRepository
import kz.kolesateam.confapp.allEvents.presentation.models.AllEventsListItem
import kz.kolesateam.confapp.allEvents.presentation.models.BranchTitleItem
import kz.kolesateam.confapp.allEvents.presentation.models.EventListItem
import kz.kolesateam.confapp.common.data.model.ProgressState
import kz.kolesateam.confapp.common.data.model.ResponseData
import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.view.DEFAULT_BRANCH_ID
import kz.kolesateam.confapp.events.presentation.view.DEFAULT_BRANCH_TITLE
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

const val TIME_FORMAT = "HH:mm:ss"

class AllEventsViewModel(
    private val allEventsRepository: AllEventsRepository
) : ViewModel() {

    val loadAllEventsLiveData: MutableLiveData<ResponseData<List<AllEventsListItem>, String>> = MutableLiveData()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    private val branchIdLiveData: MutableLiveData<Int> = MutableLiveData()
    private val branchTitleLiveData: MutableLiveData<String> = MutableLiveData()

    fun onStart(branchId: Int, branchTitle: String) {
        branchIdLiveData.value = branchId
        branchTitleLiveData.value = branchTitle

        getAllEvents()
    }

    private fun getAllEvents() = viewModelScope.launch {
        progressLiveData.value = ProgressState.Loading

        allEventsRepository.getAllEvents(
            result = {
                loadAllEventsLiveData.value = ResponseData.Success(prepareAllEvents(it))
            },
            fail = {
                loadAllEventsLiveData.value = ResponseData.Error(it.toString())
            },
            branchId = branchIdLiveData.value ?: DEFAULT_BRANCH_ID
        )

        progressLiveData.value = ProgressState.Done
    }

    private fun prepareAllEvents(
        eventsList: List<EventApiData>
    ): List<AllEventsListItem> = listOf(getBranchTitleItem()) + getEventsItem(eventsList)

    private fun getBranchTitleItem(): AllEventsListItem {
        return BranchTitleItem(
            branchTitle = branchTitleLiveData.value ?: DEFAULT_BRANCH_TITLE
        )
    }

    private fun getEventsItem(eventsList: List<EventApiData>): List<AllEventsListItem> = eventsList.map {
        EventListItem(
            data = it,
            isCompleted = checkEventTime(it.endTime)
        )
    }

    private fun checkEventTime(eventTime: String?): Boolean {
        val simpleDateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        val currentTime: Date = simpleDateFormat.parse(simpleDateFormat.format(Date())) ?: Date()
        val eventEndTime: Date = simpleDateFormat.parse(eventTime) ?: Date()

        if(currentTime.after(eventEndTime)){
            return true
        }

        return false
    }
}