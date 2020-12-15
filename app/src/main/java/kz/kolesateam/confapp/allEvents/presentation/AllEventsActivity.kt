package kz.kolesateam.confapp.allEvents.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allEvents.presentation.models.AllEventsListItem
import kz.kolesateam.confapp.allEvents.presentation.view.AllEventsAdapter
import kz.kolesateam.confapp.allEvents.presentation.viewModel.AllEventsViewModel
import kz.kolesateam.confapp.events.presentation.models.ProgressState
import kz.kolesateam.confapp.common.data.model.ResponseData
import kz.kolesateam.confapp.common.view.EventClickListener
import kz.kolesateam.confapp.events.data.EMPTY_KEY
import kz.kolesateam.confapp.events.presentation.INTENT_BRANCH_ID_KEY
import kz.kolesateam.confapp.events.presentation.INTENT_BRANCH_TITLE_KEY
import kz.kolesateam.confapp.utils.hide
import kz.kolesateam.confapp.utils.show
import kz.kolesateam.confapp.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

const val DEFAULT_INTENT_EXTRA_VALUE = 0

class AllEventsActivity : AppCompatActivity(), EventClickListener {

    private val allEventsViewModel: AllEventsViewModel by viewModel()

    private val allEventsAdapter: AllEventsAdapter by lazy {
        AllEventsAdapter(eventClickListener = this)
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var allEventsRecyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var favoritesButton: Button
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)

        initViews()
        observeLiveData()
    }

    override fun onStart() {
        super.onStart()

        allEventsViewModel.onStart(
            branchId = getBranchIdFromIntentExtra(),
            branchTitle = getBranchTitleFromIntentExtra()
        )
    }

    override fun onEventClick(eventTitle: String) {
        showToast(eventTitle)
    }

    override fun onFavouriteButtonClick(image: ImageView, eventId: Int?) {
        image.setImageResource(R.drawable.ic_favourite_fill)
    }

    private fun initViews() {
        progressBar = findViewById(R.id.activity_all_events_progress_bar)
        errorTextView = findViewById(R.id.activity_all_events_error)
        allEventsRecyclerView = findViewById(R.id.activity_all_events_recycler_view)
        allEventsRecyclerView.adapter = allEventsAdapter

        toolbar = findViewById(R.id.activity_all_events_toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        favoritesButton = findViewById(R.id.activity_all_favorites_button)
        favoritesButton.setOnClickListener {
            showToast(getString(R.string.favourite_btn_text))
        }
    }

    private fun observeLiveData() {
        allEventsViewModel.progressLiveData.observe(this, ::handleProgress)
        allEventsViewModel.loadAllEventsLiveData.observe(this, ::handleResponseAllEvents)
    }

    private fun handleProgress(progressState: ProgressState) {
        when (progressState) {
            ProgressState.Loading -> progressBar.show()
            ProgressState.Done -> progressBar.hide()
        }
    }

    private fun handleResponseAllEvents(responseData: ResponseData<List<AllEventsListItem>, String>) {
        when (responseData) {
            is ResponseData.Success -> allEventsAdapter.setList(responseData.result)
            is ResponseData.Error -> errorTextView.text = responseData.error
        }
    }

    private fun getBranchIdFromIntentExtra(): Int = intent.getIntExtra(
        INTENT_BRANCH_ID_KEY,
        DEFAULT_INTENT_EXTRA_VALUE
    )

    private fun getBranchTitleFromIntentExtra(): String {
        return intent.getStringExtra(INTENT_BRANCH_TITLE_KEY) ?: EMPTY_KEY
    }
}