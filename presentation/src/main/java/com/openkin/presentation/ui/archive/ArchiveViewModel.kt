package com.openkin.presentation.ui.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.presentation.ui.notesboard.model.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArchiveViewModel(private val notesInteractor: INotesInteractor): ViewModel() {

    private val defaultState = ArchiveState(
        notesList = listOf(),
        sortType = Pair(SortType.CREATE_DATE, true),
        prevSortType = SortType.ALPHABET,
    )
    private val _viewState = MutableStateFlow<ArchiveState>(defaultState)
    val viewState: StateFlow<ArchiveState> = _viewState.asStateFlow()
    private val _loadingState = MutableStateFlow<Boolean>(true)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    fun getArchive() {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getArchivedNotes().collect { notes ->
                _viewState.value = _viewState.value.copy(notesList = notes)
                _loadingState.value = false
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

    fun clearArchive() {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.removeAllArchivedNotes()
            _viewState.value = _viewState.value.copy(notesList = listOf())
        }
    }

    fun updateSortType(sortType: SortType, order: Boolean) {
        _viewState.value = _viewState.value.copy(sortType = Pair(sortType, order))
    }

    fun updatePrevSortType(sortType: SortType) {
        _viewState.value = _viewState.value.copy(prevSortType = sortType)
    }
}
