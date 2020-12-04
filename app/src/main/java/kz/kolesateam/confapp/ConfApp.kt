package kz.kolesateam.confapp

import android.app.Application
import kz.kolesateam.confapp.di.applicationModule
import kz.kolesateam.confapp.events.di.eventScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ConfApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ConfApp)

            modules (
                applicationModule,
                eventScreenModule
            )
        }
    }
}