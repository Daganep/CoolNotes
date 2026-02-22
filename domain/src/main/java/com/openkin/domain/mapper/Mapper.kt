package com.openkin.domain.mapper

import com.openkin.domain.model.NoteDto
import com.openkin.domain.model.NoteUi
import java.time.LocalDate
import java.time.ZoneOffset

fun NoteDto.toNoteUi(): NoteUi {
    val note = NoteUi(
        id = this.id,
        title = this.title,
        description = this.description,
        createDateMS = this.createDateMS,
    )
    note.archived = this.archived
    note.editDateMS = this.editDateMS
    note.color = this.color
    note.targetDate = this.targetDate
    return note
}

fun NoteUi.toNoteDto(): NoteDto =
    NoteDto(
        id = this.id,
        title = this.title,
        description = this.description,
        color = this.color,
        createDateMS = this.createDateMS,
        editDateMS = this.editDateMS,
        archived = this.archived,
        targetDate = this.targetDate,
    )

fun localDateDayToRangeMillis(daysList: List<LocalDate>): List<Pair<Long, Long>> {
    val resultList = mutableListOf<Pair<Long, Long>>()
    daysList.forEach { day ->
        val startOfDay = day.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        val endOfDay = day.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        resultList.add(Pair(startOfDay, endOfDay))
    }
    return resultList
}
