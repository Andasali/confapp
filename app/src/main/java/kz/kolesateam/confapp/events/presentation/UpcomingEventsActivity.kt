package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.events.data.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.net.ConnectException

const val RESPONSE_TEXT = "RESPONSE_TEXT"
const val RESPONSE_TEXT_COLOR = "RESPONSE_TEXT_COLOR"

val apiRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://37.143.8.68:2020")
    .addConverterFactory(JacksonConverterFactory.create())
    .build()

val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)

class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var upcomingEventsSyncButton: Button
    private lateinit var upcomingEventsAsyncButton: Button
    private lateinit var upcomingEventsResponseTextView: TextView
    private lateinit var upcomingEventsProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.run {
            putString("RESPONSE_TEXT", upcomingEventsResponseTextView.text.toString())
            putInt("RESPONSE_TEXT_COLOR", upcomingEventsResponseTextView.currentTextColor)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        upcomingEventsResponseTextView.text = savedInstanceState?.getString("RESPONSE_TEXT")
        upcomingEventsResponseTextView.setTextColor(savedInstanceState?.getInt("RESPONSE_TEXT_COLOR"))
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

    private fun loadApiDataSync() {
        upcomingEventsResponseTextView.text = ""
        startProgressBar()
        Thread {
            try {
                val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    runOnUiThread {
                        stopProgressBar()
                        upcomingEventsResponseTextView.setTextColor(resources.getColor(R.color.activity_upcoming_events_sync_text_view_color))
                        upcomingEventsResponseTextView.text = body.toString()
                    }
                }
            } catch (e: ConnectException) {
                runOnUiThread {
                    stopProgressBar()
                    upcomingEventsResponseTextView.setTextColor(resources.getColor(R.color.activity_upcoming_events_error_text_view_color))
                    upcomingEventsResponseTextView.text =
                        resources.getText(R.string.connection_error_message)
                }
            }
        }.start()
    }

    private fun loadApiDataAsync() {
        upcomingEventsResponseTextView.text = ""
        startProgressBar()
        apiClient.getUpcomingEvents().enqueue(object : Callback<JsonNode> {
            override fun onResponse(
                call: Call<JsonNode>,
                response: Response<JsonNode>
            ) {
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    stopProgressBar()
                    upcomingEventsResponseTextView.setTextColor(resources.getColor(R.color.activity_upcoming_events_async_text_view_color))
                    upcomingEventsResponseTextView.text = body.toString()
                }
            }

            override fun onFailure(
                call: Call<JsonNode>,
                t: Throwable
            ) {
                stopProgressBar()
                upcomingEventsResponseTextView.setTextColor(resources.getColor(R.color.activity_upcoming_events_error_text_view_color))
                upcomingEventsResponseTextView.text = t.localizedMessage
            }
        })
    }

    private fun startProgressBar() {
        upcomingEventsProgressBar.visibility = View.VISIBLE;
    }

    private fun stopProgressBar() {
        upcomingEventsProgressBar.visibility = View.INVISIBLE;
    }

}