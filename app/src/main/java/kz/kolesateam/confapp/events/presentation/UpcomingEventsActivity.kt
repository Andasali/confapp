package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.events.data.Models.BranchApiData
import kz.kolesateam.confapp.events.data.ResponseData
import kz.kolesateam.confapp.events.data.UpcomingEventsRepository
import kz.kolesateam.confapp.network.apiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val RESPONSE_TEXT = "RESPONSE_TEXT"
const val RESPONSE_TEXT_COLOR = "RESPONSE_TEXT_COLOR"

const val DATA_ASYNC_TEXT_COLOR = R.color.activity_upcoming_events_async_text_view_color
const val DATA_SYNC_TEXT_COLOR = R.color.activity_upcoming_events_sync_text_view_color
const val DATA_ERROR_TEXT_COLOR = R.color.activity_upcoming_events_error_text_view_color

class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var upcomingEventsSyncButton: Button
    private lateinit var upcomingEventsAsyncButton: Button
    private lateinit var upcomingEventsResponseTextView: TextView
    private lateinit var upcomingEventsProgressBar: ProgressBar
    private lateinit var upcomingEventsRepository: UpcomingEventsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        initViews()

        upcomingEventsRepository = UpcomingEventsRepository()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("RESPONSE_TEXT", upcomingEventsResponseTextView.text.toString())
            putInt("RESPONSE_TEXT_COLOR", upcomingEventsResponseTextView.currentTextColor)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        upcomingEventsResponseTextView.text = savedInstanceState.getString("RESPONSE_TEXT")
        upcomingEventsResponseTextView.setTextColor(savedInstanceState.getInt("RESPONSE_TEXT_COLOR"))
    }

    private fun initViews() {
        upcomingEventsSyncButton = findViewById(R.id.activity_upcoming_events_button_sync)
        upcomingEventsAsyncButton = findViewById(R.id.activity_upcoming_events_button_async)
        upcomingEventsResponseTextView =
            findViewById(R.id.activity_upcoming_events_text_view_json_result)
        upcomingEventsProgressBar = findViewById(R.id.activity_upcoming_events_progress_bar)

        upcomingEventsSyncButton.setOnClickListener {
            loadApiDataSync()
        }

        upcomingEventsAsyncButton.setOnClickListener {
            loadApiDataAsync()
        }
    }

    private fun loadApiDataSync() = CoroutineScope(Main).launch {
        startProgressBar()

        val response: ResponseData<List<BranchApiData>, String> = withContext(IO) {
            upcomingEventsRepository.getUpcomingEventsSync()
        }

        when (response) {
            is ResponseData.Success -> addText(response.result.toString(), DATA_SYNC_TEXT_COLOR)
            is ResponseData.Error -> addText(response.error, DATA_ERROR_TEXT_COLOR)
        }
    }

//    private fun loadApiDataAsync() {
//        upcomingEventsResponseTextView.text = ""
//        startProgressBar()
//        apiClient.getUpcomingEvents().enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(
//                call: Call<ResponseBody>,
//                response: Response<ResponseBody>
//            ) {
//                if (response.isSuccessful) {
//                    val body: ResponseBody = response.body()!!
//                    stopProgressBar()
//                    upcomingEventsResponseTextView.setTextColor(resources.getColor(R.color.activity_upcoming_events_async_text_view_color))
//                    upcomingEventsResponseTextView.text = body.toString()
//                }
//            }
//
//            override fun onFailure(
//                call: Call<ResponseBody>,
//                t: Throwable
//            ) {
//                stopProgressBar()
//                upcomingEventsResponseTextView.setTextColor(resources.getColor(R.color.activity_upcoming_events_error_text_view_color))
//                upcomingEventsResponseTextView.text = t.localizedMessage
//            }
//        })
//    }

    private fun addText(text: String?, color: Int) {
        stopProgressBar()

        upcomingEventsResponseTextView.text = text
        upcomingEventsResponseTextView.setTextColor(ContextCompat.getColor(this, color))
    }

    private fun startProgressBar() {
        upcomingEventsProgressBar.visibility = View.VISIBLE;
    }

    private fun stopProgressBar() {
        upcomingEventsProgressBar.visibility = View.INVISIBLE;
    }

}