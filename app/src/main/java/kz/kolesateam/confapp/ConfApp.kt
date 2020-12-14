package kz.kolesateam.confapp

import android.app.Application
import kz.kolesateam.confapp.allEvents.di.allEventsModule
import kz.kolesateam.confapp.di.applicationModule
import kz.kolesateam.confapp.events.di.eventScreenModule
import kz.kolesateam.confapp.favoriteEvents.di.favoriteEventsModule
import kz.kolesateam.confapp.notifications.NotificationHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ConfApp : Application() {
    override fun onCreate() {
        super.onCreate()

        NotificationHelper.init(this)

        startKoin {
            androidLogger()
            androidContext(this@ConfApp)

            modules (
                applicationModule,
                eventScreenModule,
                allEventsModule,
                favoriteEventsModule
            )
        }
    }
}