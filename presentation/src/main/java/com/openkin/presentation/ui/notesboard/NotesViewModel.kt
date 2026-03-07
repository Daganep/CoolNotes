package com.openkin.presentation.ui.notesboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.interactor.SettingsInteractor
import com.openkin.domain.model.NoteUi
import com.openkin.presentation.ui.addnote.model.NotificationModel
import com.openkin.presentation.ui.notesboard.model.SortType
import com.openkin.presentation.ui.notesboard.model.ViewType
import com.openkin.presentation.utils.AlarmScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val notesInteractor: INotesInteractor,
    private val settingsInteractor: SettingsInteractor,
    private val alarmScheduler: AlarmScheduler,
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
                _viewState.value = _viewState.value.copy(notesList = notes)
                _loadingState.value = false
            }
        }
    }

    fun sendNoteToArchive(note: NoteUi) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = notesInteractor.sendNoteToArchive(note.id)
            if (result) getNotes()
            if (note.notifyTime.isNotEmpty()) cancelNotify(note)
            else {
                //TODO сообщить об ошибке при перемещении в архив
            }
        }
    }

    fun saveViewType(viewType: ViewType) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsInteractor.saveViewType(viewType.typePosition)
            val state = _viewState.value
            _viewState.value = state.copy(viewType = viewType)
        }
    }

    fun getStoredViewType() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsInteractor.getStoredViewType().collect { storedViewType ->
                ViewType.entries.forEach {
                    if (it.typePosition == storedViewType) {
                        val state = _viewState.value
                        _viewState.value = state.copy(viewType = it)
                    }
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

    private fun cancelNotify(currentNote: NoteUi) {
        val notificationModel = NotificationModel(
            id = currentNote.id,
            title = currentNote.title,
            text = currentNote.text,
            targetDate = currentNote.targetDate,
            time = Pair(0, 0),
        )
        alarmScheduler.cancelNotification(notificationModel)
    }
}
