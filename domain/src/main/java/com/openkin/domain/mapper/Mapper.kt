package com.openkin.domain.mapper

import com.openkin.domain.model.NoteDto
import com.openkin.domain.model.NoteUi

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
    )
