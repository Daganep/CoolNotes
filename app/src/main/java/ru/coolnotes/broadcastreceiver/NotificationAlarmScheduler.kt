package ru.coolnotes.broadcastreceiver

import android.app.AlarmManager
import android.content.Context
import com.openkin.domain.model.NotificationModel
import com.openkin.presentation.utils.AlarmScheduler
import com.openkin.presentation.utils.getTriggerTime
import ru.coolnotes.utils.getBroadcastPendingIntent

/**
 * [NotificationAlarmScheduler] — класс для установки и отмены напоминаний, через
 * [AlarmManager]. Напоминания обрабатываются в классе [CoolNotesBroadcastReceiver], который
 * в свою очередь выводит их на экран в виде оповещений в шторке экрана.
 */

class NotificationAlarmScheduler(
    private val context: Context,
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleNotification(notification: NotificationModel) {
        val pendingIntent = getBroadcastPendingIntent(
            context = context,
            id = notification.id,
            title = notification.title,
            text = notification.text,
        )
        val triggerTime = getTriggerTime(notification.targetDate, notification.time)
        triggerTime?.let {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        }
    }

    override fun cancelNotification(notification: NotificationModel) {
        val pendingIntent = getBroadcastPendingIntent(
            context = context,
            id = notification.id,
            title = notification.title,
            text = notification.text,
        )
        alarmManager.cancel(pendingIntent)
    }
}
