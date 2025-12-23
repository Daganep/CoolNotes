package com.openkin.data.mapper

import com.openkin.data.database.model.NoteDbo
import com.openkin.domain.model.NoteDto

fun NoteDto.toNoteDbo() =
    NoteDbo(
        id = this.id,
        title = this.title,
        description = this.description,
        createDate = this.createDate,
        archived = this.archived,
    )

fun NoteDbo.toNoteDto() =
    NoteDto(
        id = this.id,
        title = this.title,
        description = this.description,
        createDate = this.createDate,
        archived = this.archived,
    )
