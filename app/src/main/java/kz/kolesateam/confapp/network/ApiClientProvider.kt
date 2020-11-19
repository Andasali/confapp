package kz.kolesateam.confapp.network

import kz.kolesateam.confapp.events.data.ApiClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = "http://37.143.8.68:2020"

object ApiClientProvider {

    fun getApiClient(): ApiClient {
        return getRetrofitApi().create(ApiClient::class.java)
    }

    private fun getRetrofitApi(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}