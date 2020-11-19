package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.network.ApiClientProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException

private const val RESPONSE_TEXT = "response_text"
private const val RESPONSE_TEXT_COLOR = "response_text_color"

private const val RESPONSE_SYNC = 0
private const val RESPONSE_ASYNC = 1
private const val RESPONSE_FAIL = -1

class UpcomingEventsActivity : AppCompatActivity() {

    private val apiClient: ApiClient = ApiClientProvider.getApiClient()
    private lateinit var syncButton: Button
    private lateinit var asyncButton: Button
    private lateinit var responseTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        initViews()
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
        responseTextView =
            findViewById(R.id.activity_upcoming_events_text_view_json_result)
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)

        syncButton.setOnClickListener {
            showProgressBar(true)
            loadApiDataSync()
        }

        asyncButton.setOnClickListener {
            showProgressBar(true)
            loadApiDataAsync()
        }
    }

    private fun loadApiDataSync() {
        Thread {
            try {
                val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
                if (response.isSuccessful) {
                    runOnUiThread {
                        getResponseResult(response.body().toString(), RESPONSE_SYNC)
                    }
                }else{
                    getResponseResult(response.errorBody().toString(), RESPONSE_FAIL)
                }
            } catch (e: ConnectException) {
                runOnUiThread {
                    getResponseResult(e.localizedMessage, RESPONSE_FAIL)
                }
            }
        }.start()
    }

    private fun loadApiDataAsync() {
        apiClient.getUpcomingEvents().enqueue(
            object : Callback<JsonNode> {
                override fun onResponse(
                    call: Call<JsonNode>,
                    response: Response<JsonNode>
                ) {
                    if (response.isSuccessful) {
                        getResponseResult(response.body().toString(), RESPONSE_ASYNC)
                    } else {
                        getResponseResult(response.errorBody().toString(), RESPONSE_FAIL)
                    }
                }

                override fun onFailure(
                    call: Call<JsonNode>,
                    t: Throwable
                ) {
                    getResponseResult(t.localizedMessage, RESPONSE_FAIL)
                }
            })
    }

    private fun getResponseResult(responseText: String?, responseType: Int) {
        showProgressBar(false)
        responseTextView.text = responseText
        val color = when (responseType) {
            RESPONSE_SYNC -> resources.getColor(R.color.activity_upcoming_events_sync_text_view_color)
            RESPONSE_ASYNC -> resources.getColor(R.color.activity_upcoming_events_async_text_view_color)
            else -> resources.getColor(R.color.activity_upcoming_events_error_text_view_color)
        }
        responseTextView.setTextColor(color)
    }

    private fun showProgressBar(isVisible: Boolean) {
        progressBar.isVisible = isVisible
    }
}