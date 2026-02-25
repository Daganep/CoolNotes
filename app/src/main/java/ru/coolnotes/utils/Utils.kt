package ru.coolnotes.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.coolnotes.broadcastreceiver.CoolNotesBroadcastReceiver

fun getPendingIntent(context: Context, id: Int, title: String, text:String): PendingIntent {
    val intent = Intent(context, CoolNotesBroadcastReceiver::class.java).apply {
        putExtra(KEY_ID, id)
        putExtra(KEY_TITLE, title)
        putExtra(KEY_TEXT, text)
    }
    val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    return PendingIntent.getBroadcast(context, id, intent, flags)
}

private const val KEY_ID = "id"
private const val KEY_TITLE = "title"
private const val KEY_TEXT = "text"
