package com.openkin.presentation.ui.notesboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.presentation.ui.notesboard.model.SortType
import com.openkin.presentation.ui.notesboard.model.ViewType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val notesInteractor: INotesInteractor,
) : ViewModel() {

    private val defaultState = NotesBoardState(
        notesList = listOf(),
        viewType = ViewType.CommonList,
        sortType = Pair(SortType.CREATE_DATE, true),
        prevSortType = SortType.ALPHABET,
    )
    private val _viewState = MutableStateFlow<NotesBoardState>(defaultState)
    val viewState: StateFlow<NotesBoardState> = _viewState.asStateFlow()
    private val _loadingState = MutableStateFlow<Boolean>(true)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getActualNotes().collect { notes ->
                val state = _viewState.value
                _viewState.value = state.copy(notesList = notes)
                _loadingState.value = false
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
            val state = _viewState.value
            _viewState.value = state.copy(viewType = viewType)
        }
    }

    fun getStoredViewType() {
        viewModelScope.launch(Dispatchers.IO) {
            val storedViewType = notesInteractor.getStoredViewType()
            ViewType.entries.forEach {
                if (it.typePosition == storedViewType) {
                    val state = _viewState.value
                    _viewState.value = state.copy(viewType = it)
                }
            }
        }
    }

    fun updateSortType(sortType: SortType, order: Boolean) {
        val state = _viewState.value
        _viewState.value = state.copy(sortType = Pair(sortType, order))
    }

    fun updatePrevSortType(sortType: SortType) {
        val state = _viewState.value
        _viewState.value = state.copy(prevSortType = sortType)
    }
}
