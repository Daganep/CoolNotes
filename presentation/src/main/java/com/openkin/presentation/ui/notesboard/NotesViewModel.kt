package com.openkin.presentation.ui.notesboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.model.NoteUi
import com.openkin.presentation.ui.notesboard.model.ViewType
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
    private val _viewTypeState = MutableStateFlow<ViewType>(ViewType.CommonList)
    val viewTypeState: StateFlow<ViewType> = _viewTypeState.asStateFlow()

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

    fun saveViewType(viewType: ViewType) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.saveViewType(viewType.typePosition)
            _viewTypeState.value = viewType
            Log.d("MyFilter", "saveViewType $viewType")
        }
    }

    fun getStoredViewType() {
        viewModelScope.launch(Dispatchers.IO) {
            val storedViewType = notesInteractor.getStoredViewType()
            ViewType.entries.forEach {
                if (it.typePosition == storedViewType) _viewTypeState.value = it
                Log.d("MyFilter", "getStoredViewType $storedViewType")
            }
        }
    }
}
