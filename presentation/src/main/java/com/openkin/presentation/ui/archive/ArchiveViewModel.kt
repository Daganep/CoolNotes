package com.openkin.presentation.ui.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.model.NoteUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArchiveViewModel(private val notesInteractor: INotesInteractor): ViewModel() {

    private val _notesState = MutableStateFlow<List<NoteUi>>(listOf())
    val notesState: StateFlow<List<NoteUi>> = _notesState.asStateFlow()

    fun getArchive() {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getArchivedNotes().collect { notes ->
                _notesState.value = notes
            }
        }
    }

    fun returnNoteToBoard(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = notesInteractor.returnNoteToBoard(noteId)
            if (result) getArchive()
            else {
                //TODO сообщить об ошибке при перемещении в архив
            }
        }
    }

    fun removeNote(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.removeNote(noteId)
            getArchive()
        }
    }
}
