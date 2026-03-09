package com.openkin.domain.mapper

import com.openkin.domain.model.NoteDto
import com.openkin.domain.model.NoteUi

fun NoteDto.toNoteUi(): NoteUi {
    val note = NoteUi(
        id = this.id,
        title = this.title,
        text = this.text,
        createDateMS = this.createDateMS,
    )
    note.archived = this.archived
    note.editDateMS = this.editDateMS
    note.color = this.color
    note.targetDate = this.targetDate
    note.notifyTime = this.notifyTime
    return note
}

fun NoteUi.toNoteDto(): NoteDto =
    NoteDto(
        id = this.id,
        title = this.title,
        text = this.text,
        color = this.color,
        createDateMS = this.createDateMS,
        editDateMS = this.editDateMS,
        archived = this.archived,
        targetDate = this.targetDate,
        notifyTime = this.notifyTime,
    )
