package kz.kolesateam.confapp.favoriteEvents.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.data.models.EventApiData
import kz.kolesateam.confapp.common.view.EventClickListener
import kz.kolesateam.confapp.favoriteEvents.data.model.EventsState
import kz.kolesateam.confapp.favoriteEvents.presentation.view.FavoriteEventsAdapter
import kz.kolesateam.confapp.favoriteEvents.presentation.viewModel.FavoriteEventsViewModel
import kz.kolesateam.confapp.utils.extensions.hide
import kz.kolesateam.confapp.utils.extensions.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteEventsActivity : AppCompatActivity(), EventClickListener {

    private val favoriteEventsViewModel: FavoriteEventsViewModel by viewModel()

    private val favoriteEventsAdapter: FavoriteEventsAdapter by lazy {
        FavoriteEventsAdapter(eventClickListener = this)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var homeButton: Button
    private lateinit var emptyEventsView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_events)

        initViews()
        observeLiveData()
    }

    override fun onStart() {
        super.onStart()

        favoriteEventsViewModel.onStart()
    }

    override fun onFavoriteButtonClicked(eventApiData: EventApiData) {
        favoriteEventsViewModel.onFavoriteButtonClick(eventApiData)
    }

    private fun initViews() {
        homeButton = findViewById(R.id.activity_all_favorites_events_button_home)
        homeButton.setOnClickListener {
            finish()
        }

        emptyEventsView = findViewById(R.id.activity_favorite_events_no_events)
        recyclerView = findViewById(R.id.activity_favorite_events_recycler_view)
        recyclerView.adapter = favoriteEventsAdapter
    }

    private fun observeLiveData() {
        favoriteEventsViewModel.favoriteEventsLiveData.observe(this, ::handleFavoriteEvents)
        favoriteEventsViewModel.emptyEventsLiveData.observe(this, ::handleFailEvents)
    }

    private fun handleFavoriteEvents(favoriteEventsList: List<EventApiData>) {
        favoriteEventsAdapter.setList(favoriteEventsList)
    }

    private fun handleFailEvents(emptyState: EventsState) {
        when(emptyState){
            EventsState.Empty -> emptyEventsView.show()
            EventsState.Full -> emptyEventsView.hide()
        }
    }
}