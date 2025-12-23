package com.openkin.domain.mapper

import com.openkin.domain.model.NoteDto
import com.openkin.domain.model.NoteUi

fun NoteDto.toNoteUi(): NoteUi {
    val note = NoteUi(
        title = this.title,
        description = this.description,
        createDate = this.createDate,
    )
    note.archived = this.archived
    return note
}

fun NoteUi.toNoteDto(): NoteDto =
    NoteDto(
        id = this.id,
        title = this.title,
        description = this.description,
        createDate = this.createDate,
        archived = this.archived,
    )
