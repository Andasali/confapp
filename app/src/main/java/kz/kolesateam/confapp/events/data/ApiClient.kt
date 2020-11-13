package kz.kolesateam.confapp.events.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {
    @GET("/upcoming_events")
    fun getUpcomingEvents() : Call<ResponseBody>
}