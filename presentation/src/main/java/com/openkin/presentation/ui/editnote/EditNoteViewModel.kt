package com.openkin.presentation.ui.editnote

import android.app.AlarmManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.model.NoteUi
import com.openkin.domain.utils.EMPTY_STRING
import com.openkin.domain.utils.NOTE_TITLE_MAX_LENGTH
import com.openkin.presentation.ui.addnote.model.NotesColors
import com.openkin.presentation.utils.getNotifyTime
import com.openkin.presentation.utils.getPendingIntent
import com.openkin.presentation.utils.getTriggerTime
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
    private val alarmManager: AlarmManager,
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

    fun onUpdateNote(context: Context) {
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

                updateNotification(context, updatedNote)
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

    private fun getNotifyTimeInt(timeString: String?): Pair<Int, Int>? {
        return if (timeString.isNullOrEmpty()) null
        else {
            val (hour, minute) = timeString.split(':')
            Pair(hour.toInt(), minute.toInt())
        }
    }

    private fun updateNotification(context: Context, updatedNote: NoteUi) {
        val currentNoteTime = _viewState.value.currentNote?.notifyTime
        val notifyTime = _viewState.value.notifyTime
        if (getNotifyTime(notifyTime) != currentNoteTime) {
            val pendingIntent = getPendingIntent(
                context = context,
                id = updatedNote.id,
                title = updatedNote.title,
                text = updatedNote.text,
            )
            alarmManager.cancel(pendingIntent)
            if (notifyTime != null) {
                val triggerTime = getTriggerTime(updatedNote.targetDate, notifyTime)
                triggerTime?.let {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                }
            }
        }
    }
}
