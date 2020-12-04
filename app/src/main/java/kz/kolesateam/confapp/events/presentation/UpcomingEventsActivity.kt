package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.UpcomingEventsRepository
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.EventClickListener
import kz.kolesateam.confapp.events.presentation.view.branchAdapter.BranchAdapter

const val EMPTY_KEY = ""
const val SHARED_PREFERENCES_KEY = "confapp"
const val USER_NAME_KEY = "user_name"

class UpcomingEventsActivity : AppCompatActivity(), EventClickListener {

    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var upcomingEventsRepository: UpcomingEventsRepository

    private val branchAdapter: BranchAdapter by lazy {
        BranchAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        initViews()
        upcomingEventsRepository = UpcomingEventsRepository()
        loadData()
    }

    private fun initViews() {
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)
        eventsRecyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        eventsRecyclerView.adapter = branchAdapter
    }

    override fun onBranchClick(branchTitle: String) {
        Toast.makeText(this, branchTitle, Toast.LENGTH_SHORT).show()
    }

    override fun onEventClick(eventTitle: String) {
        Toast.makeText(this, eventTitle, Toast.LENGTH_SHORT).show()
    }

    private fun loadData() {
        showProgressBar(true)
        upcomingEventsRepository.getUpcomingEventsAsync(
            result = {
                fillAdapterList(it)
            },
            fail = {}
        )
    }

    private fun fillAdapterList(branchList: List<BranchApiData>){
        val upcomingEventListItemList: MutableList<UpcomingEventListItem> = mutableListOf()

        val headerListItem = UpcomingEventListItem(
            type = 1,
            data = getString(R.string.welcome_fmt, getUsername())
        )
        val branchListItemList: List<UpcomingEventListItem> = branchList.map { branchApiData ->
            UpcomingEventListItem(
                type = 2,
                data = branchApiData
            )
        }

        upcomingEventListItemList.add(headerListItem)
        upcomingEventListItemList.addAll(branchListItemList)
        branchAdapter.setList(upcomingEventListItemList)

        showProgressBar(false)
    }

    private fun getUsername(): String {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_KEY, MODE_PRIVATE)
        val username = sharedPreferences.getString(USER_NAME_KEY, EMPTY_KEY)

        return username.toString()
    }

    private fun showProgressBar(isVisible: Boolean) {
        progressBar.isVisible = isVisible
    }

}