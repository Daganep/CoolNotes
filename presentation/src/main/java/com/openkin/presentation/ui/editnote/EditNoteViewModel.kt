package com.openkin.presentation.ui.editnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.model.NoteUi
import com.openkin.domain.utils.EMPTY_STRING
import com.openkin.domain.utils.NOTE_TITLE_MAX_LENGTH
import com.openkin.presentation.ui.addnote.model.NotesColors
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

@OptIn(FlowPreview::class)
class EditNoteViewModel(
    private val notesInteractor: INotesInteractor,
) : ViewModel() {

    private val defaultState = EditNoteState(
        currentNote = null,
        noteTitle = EMPTY_STRING,
        noteText = EMPTY_STRING,
        color = NotesColors.Yellow,
        isError = false,
        isNoteTitleExists = false,
        isChangeWasSaved = false,
    )
    private val _viewState = MutableStateFlow<EditNoteState>(defaultState)
    val viewState: StateFlow<EditNoteState> = _viewState.asStateFlow()
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

    fun updateNote() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value.currentNote?.let { note ->
                val updatedNote = NoteUi(
                    id = note.id,
                    title = _viewState.value.noteTitle,
                    description = _viewState.value.noteText,
                    createDateMS = note.createDateMS,
                )
                updatedNote.editDateMS = System.currentTimeMillis()
                updatedNote.color = _viewState.value.color.name
                notesInteractor.saveNote(updatedNote)
            }
            updateChangeSavedState()
        }
    }

    fun getNote(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getNote(noteId).collect { note ->
                note?.let {
                    val currentColor = NotesColors.entries.first { color ->
                        color.name == note.color
                    }
                    _newNoteTitle.value = it.title
                    val state = _viewState.value
                    _viewState.value = state.copy(
                        currentNote = note,
                        noteTitle = note.title,
                        noteText = note.description,
                        color = currentColor,
                        isNoteTitleExists = false,
                        isError = false,
                        isChangeWasSaved = false,
                    )
                }
            }
        }
    }

    fun updateTitle(title: String) {
        val state = _viewState.value
        if (title.isEmpty() || title.isBlank()) {
            _viewState.value = state.copy(
                noteTitle = title,
                isNoteTitleExists = false,
                isError = false,
                isChangeWasSaved = false,
            )
        } else if (title.length > NOTE_TITLE_MAX_LENGTH) {
            _viewState.value = state.copy(
                noteTitle = title,
                isNoteTitleExists = false,
                isError = true,
                isChangeWasSaved = false,
            )
        } else {
            _viewState.value = state.copy(
                noteTitle = title,
                isError = false,
                isChangeWasSaved = false,
            )
        }
        _newNoteTitle.value = title
    }

    fun updateNoteText(newText: String) {
        val state = _viewState.value
        _viewState.value = state.copy(
            noteText = newText,
            isChangeWasSaved = false,
        )
    }

    fun updateCurrentColor(newColor: NotesColors) {
        val state = _viewState.value
        _viewState.value = state.copy(
            color = newColor,
            isChangeWasSaved = false,
        )
    }

    private fun updateChangeSavedState() {
        val state = _viewState.value
        _viewState.value = state.copy(isChangeWasSaved = true)
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
}
