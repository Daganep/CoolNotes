package ru.coolnotes.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.provider.Settings
import androidx.datastore.preferences.core.edit
import com.openkin.domain.utils.NOTIFY_CHANNEL_DESCRIPTION
import com.openkin.domain.utils.NOTIFY_CHANNEL_ID
import com.openkin.domain.utils.NOTIFY_CHANNEL_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.coolnotes.di.appModule
import ru.coolnotes.di.viewModelModule
import ru.coolnotes.extensions.StateDataStore

class CoolNotesApp : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch { this@CoolNotesApp.StateDataStore.edit { it.clear() } }

        createNotificationChannel()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CoolNotesApp)
            modules(listOf(viewModelModule, appModule))
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFY_CHANNEL_ID,
            NOTIFY_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH,
        )
        channel.description = NOTIFY_CHANNEL_DESCRIPTION
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        channel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, audioAttributes)
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)
    }
}
