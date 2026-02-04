package com.openkin.presentation.ui.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.model.NoteUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteViewModel(
    private val notesInteractor: INotesInteractor,
) : ViewModel() {

    fun saveNote(noteTitle: String, noteText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTimeMS = System.currentTimeMillis()
            notesInteractor.saveNote(
                NoteUi(
                    id = getNoteId(noteTitle, noteText, currentTimeMS),
                    title = noteTitle,
                    description = noteText,
                    createDateMS = currentTimeMS,
                )
            )
        }
    }

    private fun getNoteId(title: String, noteText: String, createDateMS: Long): Int =
        title.hashCode() + noteText.hashCode() + createDateMS.hashCode() * 31
}
