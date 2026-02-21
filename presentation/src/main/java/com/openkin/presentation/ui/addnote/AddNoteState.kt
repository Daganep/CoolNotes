package com.openkin.presentation.ui.addnote

import com.openkin.presentation.ui.addnote.model.NotesColors
import java.time.LocalDate

data class AddNoteState(
    val noteTitle: String,
    val noteText: String,
    val color: NotesColors,
    val isError: Boolean,
    val isNoteTitleExists: Boolean,
    val selectedDate: LocalDate,
)
