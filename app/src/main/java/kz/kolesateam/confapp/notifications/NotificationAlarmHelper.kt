package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.common.data.models.EventApiData
import java.util.*

const val NOTIFICATION_CONTENT_KEY = "notification_title"

class NotificationAlarmHelper(
    private val application: Application
) {

    fun createNotificationAlarm(
        eventApiData: EventApiData
    ){
        val alarmManager: AlarmManager? = application.getSystemService(
            Context.ALARM_SERVICE
        ) as? AlarmManager

        val pendingIntent: PendingIntent = Intent(application, NotificationAlarmBroadcastReceiver::class.java).apply {
            putExtra(NOTIFICATION_CONTENT_KEY, eventApiData.title)
        }.let {
            PendingIntent.getBroadcast(application, eventApiData.id ?: 0, it, 0)
        }

        val startHour = eventApiData.startTime?.split(":")?.get(0)?.toInt() ?: 0
        val startMinute = eventApiData.startTime?.split(":")?.get(1)?.toInt() ?: 0

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, startHour)
        calendar.set(Calendar.MINUTE, startMinute)
        calendar.set(Calendar.MINUTE, -5)
        calendar.set(Calendar.SECOND, 0)

        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    fun cancelNotificationAlarm(
        eventApiData: EventApiData
    ){
        val alarmManager: AlarmManager? = application.getSystemService(
            Context.ALARM_SERVICE
        ) as? AlarmManager

        val pendingIntent: PendingIntent = Intent(application,NotificationAlarmBroadcastReceiver::class.java).let {
            PendingIntent.getBroadcast(application, eventApiData.id ?: 0, it, 0)
        }

        alarmManager?.cancel(pendingIntent)
    }
}