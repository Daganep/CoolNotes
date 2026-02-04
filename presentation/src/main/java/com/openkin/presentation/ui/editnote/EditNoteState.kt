package com.openkin.presentation.ui.editnote

import com.openkin.domain.model.NoteUi
import com.openkin.presentation.ui.addnote.model.NotesColors

data class EditNoteState(
    val currentNote: NoteUi?,
    val noteTitle: String,
    val noteText: String,
    val color: NotesColors,
    val isError: Boolean,
    val isNoteTitleExists: Boolean,
    val isChangeWasSaved: Boolean,
)
