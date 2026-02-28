package com.openkin.presentation.broadcastreceiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.openkin.domain.utils.EMPTY_STRING
import com.openkin.domain.utils.NOTIFY_CHANNEL_ID
import com.openkin.domain.utils.NOTIFY_KEY_ID
import com.openkin.domain.utils.NOTIFY_KEY_TEXT
import com.openkin.domain.utils.NOTIFY_KEY_TITLE
import com.openkin.presentation.R

class CoolNotesBroadcastReceiver: BroadcastReceiver() {

    @androidx.annotation.RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {

            val notificationId = intent?.getIntExtra(NOTIFY_KEY_ID, 0)
            val title = intent?.getStringExtra(NOTIFY_KEY_TITLE)
            val text = intent?.getStringExtra(NOTIFY_KEY_TEXT) ?: EMPTY_STRING

            if (notificationId != null && title != null) {
                val notificationManager = NotificationManagerCompat.from(context)
                val builder = NotificationCompat.Builder(context, NOTIFY_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notes_app_status_bar)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

                if (ActivityCompat.checkSelfPermission(it, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    notificationManager.notify(notificationId, builder.build())
                }
            }
        }
    }
}
