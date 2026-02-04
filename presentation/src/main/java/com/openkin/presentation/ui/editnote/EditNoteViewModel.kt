package com.openkin.presentation.ui.editnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.model.NoteUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditNoteViewModel(
    private val notesInteractor: INotesInteractor,
) : ViewModel() {

    private val _currentNote = MutableStateFlow<NoteUi?>(null)
    val currentNote: StateFlow<NoteUi?> = _currentNote.asStateFlow()

    fun updateNote(noteTitle: String, noteText: String, note: NoteUi) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedNote = NoteUi(
                id = note.id,
                title = noteTitle,
                description = noteText,
                createDateMS = note.createDateMS,
            )
            updatedNote.editDateMS = System.currentTimeMillis()
            notesInteractor.saveNote(updatedNote)
        }
    }

    fun getNote(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getNote(noteId).collect { note ->
                _currentNote.value = note
            }
        }
    }
}
