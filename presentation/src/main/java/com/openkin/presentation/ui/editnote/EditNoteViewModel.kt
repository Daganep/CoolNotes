package com.openkin.presentation.ui.editnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.model.NoteUi
import com.openkin.domain.utils.EMPTY_STRING
import com.openkin.domain.utils.NOTE_TITLE_MAX_LENGTH
import com.openkin.presentation.ui.addnote.model.NotesColors
import com.openkin.presentation.ui.addnote.model.NotificationModel
import com.openkin.presentation.utils.AlarmScheduler
import com.openkin.presentation.utils.getNotifyTime
import com.openkin.presentation.utils.getNotifyTimeInt
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
class EditNoteViewModel(
    private val notesInteractor: INotesInteractor,
    private val alarmScheduler: AlarmScheduler,
) : ViewModel() {

    private val defaultState = EditNoteState(
        currentNote = null,
        noteTitle = EMPTY_STRING,
        noteText = EMPTY_STRING,
        color = NotesColors.Yellow,
        targetDate = LocalDate.now(),
        notifyTime = null,
        isError = false,
        isArchived = false,
        isNoteTitleExists = false,
        isNoteWasChanged = false,
        isChangeWasSaved = false,
    )
    private val _viewState = MutableStateFlow<EditNoteState>(defaultState)
    val viewState: StateFlow<EditNoteState> = _viewState.asStateFlow()
    private val _editNoteEvent = MutableStateFlow<Boolean>(false)
    val editNoteEvent: StateFlow<Boolean> = _editNoteEvent.asStateFlow()
    private val _newNoteTitle = MutableStateFlow<String>(EMPTY_STRING)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _newNoteTitle
                .debounce(300L)
                .filterNot { query -> query.isEmpty() || query.isBlank() }
                .filterNot { query -> query == _viewState.value.currentNote?.title }
                .distinctUntilChanged()
                .collectLatest { title ->
                    checkTitleExists(title)
                }
        }
    }

    fun onUpdateNote() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value.currentNote?.let { note ->
                val isArchived = _viewState.value.isArchived
                val notifyTime = if (!isArchived) {
                    getNotifyTime(_viewState.value.notifyTime)
                } else {
                    EMPTY_STRING
                }
                val updatedNote = NoteUi(
                    id = note.id,
                    title = _viewState.value.noteTitle,
                    text = _viewState.value.noteText,
                    createDateMS = note.createDateMS,
                )
                updatedNote.editDateMS = System.currentTimeMillis()
                updatedNote.archived = isArchived
                updatedNote.color = _viewState.value.color.name
                updatedNote.targetDate = _viewState.value.targetDate
                updatedNote.notifyTime = notifyTime
                notesInteractor.saveNote(updatedNote)

                updateNotification(updatedNote)
            }
            updateChangeSavedState()
        }
    }

    fun onLoadNote(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getNote(noteId).collect { note ->
                note?.let {
                    val currentColor = NotesColors.entries.first { color ->
                        color.name == note.color
                    }
                    val notifyTime = getNotifyTimeInt(it.notifyTime)
                    _newNoteTitle.value = it.title
                    _viewState.value = _viewState.value.copy(
                        currentNote = note,
                        noteTitle = note.title,
                        noteText = note.text,
                        color = currentColor,
                        targetDate = note.targetDate,
                        notifyTime = notifyTime,
                        isNoteTitleExists = false,
                        isError = false,
                        isNoteWasChanged = false,
                        isChangeWasSaved = false,
                    )
                }
            }
        }
    }

    fun onUpdateTitle(title: String) {
        val isNoteWasChanged = title != _viewState.value.currentNote?.title
        if (title.isEmpty() || title.isBlank()) {
            _viewState.value = _viewState.value.copy(
                noteTitle = title,
                isNoteTitleExists = false,
                isError = false,
                isNoteWasChanged = isNoteWasChanged,
                isChangeWasSaved = false,
            )
        } else if (title.length > NOTE_TITLE_MAX_LENGTH) {
            _viewState.value = _viewState.value.copy(
                noteTitle = title,
                isNoteTitleExists = false,
                isError = true,
                isNoteWasChanged = isNoteWasChanged,
                isChangeWasSaved = false,
            )
        } else {
            _viewState.value = _viewState.value.copy(
                noteTitle = title,
                isError = false,
                isNoteWasChanged = isNoteWasChanged,
                isChangeWasSaved = false,
            )
        }
        _newNoteTitle.value = title
        _editNoteEvent.value = false
    }

    fun onUpdateNoteText(newText: String) {
        val isNoteWasChanged = newText != _viewState.value.currentNote?.text
        _viewState.value = _viewState.value.copy(
            noteText = newText,
            isNoteWasChanged = isNoteWasChanged,
            isChangeWasSaved = false,
        )
        _editNoteEvent.value = false
    }

    fun onUpdateCurrentColor(newColor: NotesColors) {
        val isNoteWasChanged = newColor.name != _viewState.value.currentNote?.color
        _viewState.value = _viewState.value.copy(
            color = newColor,
            isNoteWasChanged = isNoteWasChanged,
            isChangeWasSaved = false,
        )
        _editNoteEvent.value = false
    }

    fun onArchiveClicked() {
        val isArchived = !_viewState.value.isArchived
        val isNoteWasChanged = isArchived != _viewState.value.currentNote?.archived
        _viewState.value = _viewState.value.copy(
            isArchived = isArchived,
            isNoteWasChanged = isNoteWasChanged,
            isChangeWasSaved = false,
        )
        _editNoteEvent.value = false
    }

    fun onTargetDateChanged(newDate: LocalDate) {
        val isNoteWasChanged = newDate != _viewState.value.currentNote?.targetDate
        _viewState.value = _viewState.value.copy(
            targetDate = newDate,
            isNoteWasChanged = isNoteWasChanged,
            isChangeWasSaved = false,
        )
    }

    fun onNotifyTimeChanged(time: Pair<Int, Int>?) {
        val currentNoteTime = _viewState.value.currentNote?.notifyTime
        val isNoteWasChanged = time != getNotifyTimeInt(currentNoteTime)
        _viewState.value = _viewState.value.copy(
            notifyTime = time,
            isNoteWasChanged = isNoteWasChanged,
            isChangeWasSaved = false,
        )
    }

    private fun updateChangeSavedState() {
        _viewState.value = _viewState.value.copy(isChangeWasSaved = true)
        _editNoteEvent.value = true
    }

    private fun checkTitleExists(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _viewState.value
            val isTitleExists = notesInteractor.checkTitleExists(title)
            _viewState.value = state.copy(
                isNoteTitleExists = isTitleExists,
                isError = isTitleExists,
            )
        }
    }

    private fun updateNotification(updatedNote: NoteUi) {
        val currentNoteTime = _viewState.value.currentNote?.notifyTime
        if (updatedNote.notifyTime != currentNoteTime) {
            cancelPreviousAlarm(updatedNote)
            _viewState.value.notifyTime?.let { time ->
                scheduleAlarm(updatedNote, time)
            }
        }
    }

    private fun cancelPreviousAlarm(updatedNote: NoteUi) {
        val notificationModel = NotificationModel(
            id = updatedNote.id,
            title = updatedNote.title,
            text = updatedNote.text,
            targetDate = updatedNote.targetDate,
            time = Pair(0, 0)
        )
        alarmScheduler.cancelNotification(notificationModel)
    }

    private fun scheduleAlarm(updatedNote: NoteUi, notifyTime: Pair<Int, Int>) {
        val notificationModel = NotificationModel(
            id = updatedNote.id,
            title = updatedNote.title,
            text = updatedNote.text,
            targetDate = updatedNote.targetDate,
            time = notifyTime
        )
        alarmScheduler.scheduleNotification(notificationModel)
    }
}
