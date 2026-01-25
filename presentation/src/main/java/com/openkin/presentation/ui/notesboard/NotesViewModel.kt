package com.openkin.presentation.ui.notesboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.model.NoteUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val notesInteractor: INotesInteractor,
) : ViewModel() {

    private val _notesState = MutableStateFlow<List<NoteUi>>(listOf())
    val notesState: StateFlow<List<NoteUi>> = _notesState.asStateFlow()

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getActualNotes().collect { notes ->
                _notesState.value = notes
            }
        }
    }

    fun sendNoteToArchive(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = notesInteractor.sendNoteToArchive(noteId)
            if (result) getNotes()
            else {
                //TODO сообщить об ошибке при перемещении в архив
            }
        }
    }
}
