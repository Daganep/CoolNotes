package ru.coolnotes.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.openkin.domain.utils.NOTIFY_KEY_ID
import com.openkin.domain.utils.NOTIFY_KEY_TEXT
import com.openkin.domain.utils.NOTIFY_KEY_TITLE
import ru.coolnotes.MainActivity
import ru.coolnotes.broadcastreceiver.CoolNotesBroadcastReceiver

fun getBroadcastPendingIntent(context: Context, id: Int, title: String, text:String): PendingIntent {
    val intent = Intent(context, CoolNotesBroadcastReceiver::class.java).apply {
        putExtra(NOTIFY_KEY_ID, id)
        putExtra(NOTIFY_KEY_TITLE, title)
        putExtra(NOTIFY_KEY_TEXT, text)
    }
    val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    return PendingIntent.getBroadcast(context, id, intent, flags)
}

fun getActivityPendingIntent(context: Context, notificationId: Int): PendingIntent {
    val activityIntent = Intent(context, MainActivity::class.java).apply {
        putExtra(NOTIFY_KEY_ID, notificationId)
    }
    return PendingIntent.getActivity(
        context,
        notificationId,
        activityIntent,
        PendingIntent.FLAG_IMMUTABLE,
    )
}
