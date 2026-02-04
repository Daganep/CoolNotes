package com.openkin.presentation.ui.addnote

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
class AddNoteViewModel(
    private val notesInteractor: INotesInteractor,
) : ViewModel() {

    private val defaultState = AddNoteState(
        noteTitle = EMPTY_STRING,
        noteText = EMPTY_STRING,
        color = NotesColors.Yellow,
        isError = false,
        isNoteTitleExists = false,
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

    fun saveNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val title = _viewState.value.noteTitle
            val text = _viewState.value.noteText
            val color = _viewState.value.color
            val currentTimeMS = System.currentTimeMillis()
            val newNote = NoteUi(
                id = getNoteId(title, text, currentTimeMS),
                title = title,
                description = text,
                createDateMS = currentTimeMS,
            )
            newNote.color = color.name
            notesInteractor.saveNote(newNote)
        }
    }

    fun updateTitle(title: String) {
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

    fun updateNoteText(newText: String) {
        val state = _viewState.value
        _viewState.value = state.copy(noteText = newText)
    }

    fun updateCurrentColor(newColor: NotesColors) {
        val state = _viewState.value
        _viewState.value = state.copy(color = newColor)
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

    private fun getNoteId(title: String, noteText: String, createDateMS: Long): Int =
        title.hashCode() + noteText.hashCode() + createDateMS.hashCode() * 31
}
