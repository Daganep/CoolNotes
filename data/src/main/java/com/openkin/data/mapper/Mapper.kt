package com.openkin.data.mapper

import com.openkin.data.database.model.NoteDbo
import com.openkin.domain.model.NoteDto
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
    )

fun NoteDbo.toNoteDto() =
    NoteDto(
        id = this.id,
        title = this.title,
        description = this.description,
        color = this.color,
        createDateMS = this.createDateMS,
        editDateMS = this.editDateMS,
        archived = this.archived,
        targetDate = localDateFromString(this.targetDate),
    )

fun localDateToString(date: LocalDate): String =
    "${date.year};${date.month.value};${date.dayOfMonth}"

fun localDateFromString(date: String): LocalDate {
    val (year, month, day) = date.split(';')
    return LocalDate.of(year.toInt(), month.toInt(), day.toInt())
}
