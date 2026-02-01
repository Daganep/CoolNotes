package com.openkin.presentation.ui.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.model.NoteUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNoteViewModel(
    private val notesInteractor: INotesInteractor,
) : ViewModel() {

    private val _currentNote = MutableStateFlow<NoteUi?>(null)
    val currentNote: StateFlow<NoteUi?> = _currentNote.asStateFlow()

    fun saveNote(noteTitle: String, noteText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.saveNote(
                NoteUi(
                    title = noteTitle,
                    description = noteText,
                    createDateMS = System.currentTimeMillis(),
                )
            )
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
