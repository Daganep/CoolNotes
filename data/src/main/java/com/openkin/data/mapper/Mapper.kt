package com.openkin.data.mapper

import com.openkin.data.database.model.NoteDbo
import com.openkin.data.utils.isReminderInPast
import com.openkin.domain.model.NoteDto
import com.openkin.domain.utils.EMPTY_STRING
import java.time.LocalDate

fun NoteDto.toNoteDbo() =
    NoteDbo(
        id = this.id,
        title = this.title,
        description = this.description,
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
    val notifyTime = if (isReminderInPast) EMPTY_STRING else time
    return NoteDto(
        id = this.id,
        title = this.title,
        description = this.description,
        color = this.color,
        createDateMS = this.createDateMS,
        editDateMS = this.editDateMS,
        archived = this.archived,
        targetDate = targetDate,
        notifyTime = notifyTime,
    )
}

fun localDateToString(date: LocalDate): String =
    "${date.year};${date.month.value};${date.dayOfMonth}"

fun localDateFromString(date: String): LocalDate {
    val (year, month, day) = date.split(';')
    return LocalDate.of(year.toInt(), month.toInt(), day.toInt())
}
