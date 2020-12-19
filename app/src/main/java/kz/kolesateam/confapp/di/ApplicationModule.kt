package kz.kolesateam.confapp.di

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import kz.kolesateam.confapp.common.data.DefaultEventsMapper
import kz.kolesateam.confapp.common.domain.EventsMapper
import kz.kolesateam.confapp.events.data.UserNameSharedPrefsDataSource
import kz.kolesateam.confapp.events.domain.UserNameDataSource
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = "http://37.143.8.68:2020"

const val SHARED_PREFERENCES_KEY = "shared_prefs"
const val SHARED_PREFS_DATA_SOURCE = "shared_prefs_data_source"

val applicationModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    single {
        val context = androidApplication()

        context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    single {
        ObjectMapper()
    }

    single {
        DefaultEventsMapper(
            favoriteEventsRepository = get()
        ) as EventsMapper
    }

    single {
        NotificationAlarmHelper(
            application = androidApplication()
        )
    }

    factory(named(SHARED_PREFS_DATA_SOURCE)) {
        UserNameSharedPrefsDataSource(
            sharedPreferences = get()
        ) as UserNameDataSource
    }
}