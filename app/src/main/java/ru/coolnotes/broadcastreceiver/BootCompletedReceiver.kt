package ru.coolnotes.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.openkin.data.mapper.toNotificationModel
import com.openkin.data.utils.isReminderInPast
import com.openkin.domain.repository.INoteRepository
import com.openkin.presentation.utils.AlarmScheduler
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * [BootCompletedReceiver] — класс для получения сообшений от операционной системы, о том что
 * устройство было перезагружено. Так как перезагрузка отменяет все установленные через
 * [AlarmScheduler] оповещения, то после перезагрузки они заново устанавоиваются
 * в методе [onReceive]
 */

class BootCompletedReceiver : BroadcastReceiver(), KoinComponent {

    private val notesRepository: INoteRepository by inject()
    private val alarmScheduler: AlarmScheduler by inject()

    override fun onReceive(context: Context?, intent: Intent?) = runBlocking {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val notesList = notesRepository.getActualNotes()
                .map { notes ->
                    notes
                        .filter { it.notifyTime.isNotEmpty() }
                        .filter { !isReminderInPast(it.targetDate, it.notifyTime) }
                }

            notesList.collect { notes ->
                notes.forEach { alarmScheduler.scheduleNotification(it.toNotificationModel()) }
            }
        }
    }
}
