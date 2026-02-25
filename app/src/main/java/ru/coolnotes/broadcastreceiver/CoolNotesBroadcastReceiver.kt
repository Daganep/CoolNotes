package ru.coolnotes.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.openkin.domain.utils.NOTIFY_CHANNEL_ID
import com.openkin.presentation.R

class CoolNotesBroadcastReceiver: BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {

            val permission = ActivityCompat
                .checkSelfPermission(it, android.Manifest.permission.POST_NOTIFICATIONS)
            if (permission != PackageManager.PERMISSION_GRANTED) return

            val text = intent?.getStringExtra("text") ?: "Reminder Text"
            val notificationId = intent?.getIntExtra("id", 0)

            notificationId?.let { id ->
                val notificationManager = NotificationManagerCompat.from(context)
                val title = context.resources.getString(R.string.add_note_empty_timer)
                val builder = NotificationCompat.Builder(context, NOTIFY_CHANNEL_ID)
                    .setSmallIcon(R.drawable.image_notes_app)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                notificationManager.notify(id, builder.build())
            }
        }
    }
}
