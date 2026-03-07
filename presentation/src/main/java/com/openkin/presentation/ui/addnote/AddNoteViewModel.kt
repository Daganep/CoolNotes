package com.openkin.presentation.ui.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.interactor.SettingsInteractor
import com.openkin.domain.model.NoteUi
import com.openkin.domain.utils.EMPTY_STRING
import com.openkin.domain.utils.NOTE_TITLE_MAX_LENGTH
import com.openkin.presentation.ui.addnote.model.NotesColors
import com.openkin.presentation.ui.addnote.model.NotificationModel
import com.openkin.presentation.utils.AlarmScheduler
import com.openkin.presentation.utils.getNotifyTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(FlowPreview::class)
class AddNoteViewModel(
    private val notesInteractor: INotesInteractor,
    private val settingsInteractor: SettingsInteractor,
    private val alarmScheduler: AlarmScheduler,
) : ViewModel() {

    private val defaultState = AddNoteState(
        noteTitle = EMPTY_STRING,
        noteText = EMPTY_STRING,
        color = NotesColors.Yellow,
        isError = false,
        isNoteTitleExists = false,
        targetDate = LocalDate.now(),
        notifyTime = null,
    )
    private val _viewState = MutableStateFlow<AddNoteState>(defaultState)
    val viewState: StateFlow<AddNoteState> = _viewState.asStateFlow()
    private val _newNoteTitle = MutableStateFlow<String>(EMPTY_STRING)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _newNoteTitle
                .debounce(300L)
                .filterNot { query -> query.isEmpty() || query.isBlank() }
                .distinctUntilChanged()
                .collectLatest { title ->
                    checkTitleExists(title)
                }
        }
    }

    fun onSaveNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val title = _viewState.value.noteTitle
            val text = _viewState.value.noteText
            val selectedTime = _viewState.value.notifyTime
            val notifyTime = getNotifyTime(selectedTime)
            val currentTimeMS = System.currentTimeMillis()
            val id = getNoteId(title, text, currentTimeMS)
            val newNote = NoteUi(
                id = id,
                title = title,
                text = text,
                createDateMS = currentTimeMS,
            )
            newNote.color = _viewState.value.color.name
            newNote.targetDate = _viewState.value.targetDate
            newNote.notifyTime = notifyTime
            notesInteractor.saveNote(newNote)
            if (selectedTime != null) scheduleNotification(newNote, selectedTime)
        }
    }

    fun onUpdateTitle(title: String) {
        val state = _viewState.value
        if (title.isEmpty() || title.isBlank()) {
            _viewState.value = state.copy(
                noteTitle = title,
                isNoteTitleExists = false,
                isError = false,
            )
        } else if (title.length > NOTE_TITLE_MAX_LENGTH) {
            _viewState.value = state.copy(
                noteTitle = title,
                isNoteTitleExists = false,
                isError = true,
            )
        } else {
            _viewState.value = state.copy(
                noteTitle = title,
                isError = false,
            )
        }
        _newNoteTitle.value = title
    }

    fun onUpdateNoteText(newText: String) {
        _viewState.value = _viewState.value.copy(noteText = newText)
    }

    fun updateCurrentColor(newColor: NotesColors) {
        _viewState.value = _viewState.value.copy(color = newColor)
    }

    fun onTargetDateChanged(newDate: LocalDate) {
        _viewState.value = _viewState.value.copy(targetDate = newDate)
    }

    fun onNotifyTimeChanged(time: Pair<Int, Int>?) {
        _viewState.value = _viewState.value.copy(notifyTime = time)
    }

    fun getNotifyRequestStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val previouslyRequested = settingsInteractor.getNotifyRequestStatus()
        }
    }

    fun updateNotifyRequestStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsInteractor.updateNotifyRequestStatus()
        }
    }

    private fun scheduleNotification(newNote: NoteUi, selectedTime: Pair<Int, Int>) {
        alarmScheduler.scheduleNotification(
            NotificationModel(
                id = newNote.id,
                title = newNote.title,
                text = newNote.text,
                targetDate = newNote.targetDate,
                time = selectedTime,
            ))
    }

    private fun checkTitleExists(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isTitleExists = notesInteractor.checkTitleExists(title)
            _viewState.value = _viewState.value.copy(
                isNoteTitleExists = isTitleExists,
                isError = isTitleExists,
            )
        }
    }

    private fun getNoteId(title: String, noteText: String, createDateMS: Long): Int =
        title.hashCode() + noteText.hashCode() + createDateMS.hashCode() * 31
}
