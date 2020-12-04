package kz.kolesateam.confapp.di

import android.content.Context
import kz.kolesateam.confapp.events.data.UserNameSharedPrefsDataSource
import kz.kolesateam.confapp.events.domain.UserNameDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

const val SHARED_PREFERENCES_KEY = "shared_prefs"

val applicationModule = module {

    single {
        val context = androidApplication()

        context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    factory {
        UserNameSharedPrefsDataSource(
            sharedPreferences = get()
        ) as UserNameDataSource
    }
}