package com.openkin.data.mapper

import com.openkin.data.database.model.NoteDbo
import com.openkin.data.utils.getNotifyTimeInt
import com.openkin.data.utils.isReminderInPast
import com.openkin.domain.model.NoteDto
import com.openkin.domain.model.NotificationModel
import com.openkin.domain.utils.EMPTY_STRING
import java.time.LocalDate

fun NoteDto.toNoteDbo() =
    NoteDbo(
        id = this.id,
        title = this.title,
        description = this.text,
        color = this.color,
        createDateMS = this.createDateMS,
        editDateMS = this.editDateMS,
        archived = this.archived,
        targetDate = localDateToString(this.targetDate),
        notifyTime = this.notifyTime,
    )

fun NoteDbo.toNoteDto(): NoteDto {
    val targetDate = localDateFromString(this.targetDate)
    val time = this.notifyTime
    val isReminderInPast = if (time.isEmpty()) true else isReminderInPast(targetDate, time)
    val notifyTime = if (isReminderInPast || this.archived) EMPTY_STRING else time
    return NoteDto(
        id = this.id,
        title = this.title,
        text = this.description,
        color = this.color,
        createDateMS = this.createDateMS,
        editDateMS = this.editDateMS,
        archived = this.archived,
        targetDate = targetDate,
        notifyTime = notifyTime,
    )
}

fun NoteDto.toNotificationModel(): NotificationModel {
    val time = getNotifyTimeInt(this.notifyTime)
    return NotificationModel(
        id = this.id,
        title = this.title,
        text = this.text,
        targetDate = this.targetDate,
        time = time ?: Pair(0, 0),
    )
}

fun localDateToString(date: LocalDate): String =
    "${date.year};${date.month.value};${date.dayOfMonth}"

fun localDateFromString(date: String): LocalDate {
    val (year, month, day) = date.split(';')
    return LocalDate.of(year.toInt(), month.toInt(), day.toInt())
}
