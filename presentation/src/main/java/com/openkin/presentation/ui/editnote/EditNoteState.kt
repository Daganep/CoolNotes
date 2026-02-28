package com.openkin.presentation.ui.editnote

import com.openkin.domain.model.NoteUi
import com.openkin.presentation.ui.addnote.model.NotesColors
import java.time.LocalDate

data class EditNoteState(
    val currentNote: NoteUi?,
    val noteTitle: String,
    val noteText: String,
    val color: NotesColors,
    val targetDate: LocalDate,
    val notifyTime: Pair<Int, Int>?,
    val isError: Boolean,
    val isArchived: Boolean,
    val isNoteTitleExists: Boolean,
    val isNoteWasChanged: Boolean,
    val isChangeWasSaved: Boolean,
)
