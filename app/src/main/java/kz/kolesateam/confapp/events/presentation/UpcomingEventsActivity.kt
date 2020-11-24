package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.ResponseData
import kz.kolesateam.confapp.events.data.UpcomingEventsRepository


const val RESPONSE_TEXT = "RESPONSE_TEXT"
const val RESPONSE_TEXT_COLOR = "RESPONSE_TEXT_COLOR"

const val DATA_ASYNC_TEXT_COLOR = R.color.activity_upcoming_events_async_text_view_color
const val DATA_SYNC_TEXT_COLOR = R.color.activity_upcoming_events_sync_text_view_color
const val DATA_ERROR_TEXT_COLOR = R.color.activity_upcoming_events_error_text_view_color

class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var syncButton: Button
    private lateinit var asyncButton: Button
    private lateinit var responseTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var upcomingEventsRepository: UpcomingEventsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        initViews()
        upcomingEventsRepository = UpcomingEventsRepository()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(RESPONSE_TEXT, responseTextView.text.toString())
            putInt(RESPONSE_TEXT_COLOR, responseTextView.currentTextColor)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        responseTextView.text = savedInstanceState.getString(RESPONSE_TEXT)
        responseTextView.setTextColor(savedInstanceState.getInt(RESPONSE_TEXT_COLOR))
    }

    private fun initViews() {
        syncButton = findViewById(R.id.activity_upcoming_events_button_sync)
        asyncButton = findViewById(R.id.activity_upcoming_events_button_async)
        responseTextView = findViewById(R.id.activity_upcoming_events_text_view_json_result)
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)

        syncButton.setOnClickListener {
            loadApiDataSync()
        }

        asyncButton.setOnClickListener {
            loadApiDataAsync()
        }
    }

    private fun loadApiDataSync() = CoroutineScope(Main).launch {
        showProgressBar(true)

        val response: ResponseData<List<BranchApiData>, String> = withContext(IO) {
            upcomingEventsRepository.getUpcomingEventsSync()
        }

        when (response) {
            is ResponseData.Success -> showResult(response.result.toString(), DATA_SYNC_TEXT_COLOR)
            is ResponseData.Error -> showResult(response.error, DATA_ERROR_TEXT_COLOR)
        }
    }

    private fun loadApiDataAsync() {
        showProgressBar(true)

        upcomingEventsRepository.getUpcomingEventsAsync(
            result = {
                showResult(it.toString(), DATA_ASYNC_TEXT_COLOR)
            },
            fail = {
                showResult(it, DATA_ERROR_TEXT_COLOR)
            }
        )
    }

    private fun showResult(text: String?, color: Int) {
        showProgressBar(false)

        responseTextView.text = text
        responseTextView.setTextColor(ContextCompat.getColor(this, color))
    }

    private fun showProgressBar(isVisible: Boolean) {
        progressBar.isVisible = isVisible
    }

}