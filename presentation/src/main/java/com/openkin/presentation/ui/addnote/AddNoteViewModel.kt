package com.openkin.presentation.ui.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.model.NoteUi
import com.openkin.domain.utils.EMPTY_STRING
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

    private val _newNoteTitle = MutableStateFlow<String>(EMPTY_STRING)
    val newNoteTitle: StateFlow<String> = _newNoteTitle.asStateFlow()
    private val _noteExists = MutableStateFlow<Boolean>(false)
    val noteExists: StateFlow<Boolean> = _noteExists.asStateFlow()

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

    fun saveNote(noteTitle: String, noteText: String, color: NotesColors) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTimeMS = System.currentTimeMillis()
            val newNote = NoteUi(
                id = getNoteId(noteTitle, noteText, currentTimeMS),
                title = noteTitle,
                description = noteText,
                createDateMS = currentTimeMS,
            )
            newNote.color = color.name
            notesInteractor.saveNote(newNote)
        }
    }

    fun updateTitle(title: String) {
        if (title.isEmpty() || title.isBlank()) _noteExists.value = false
        _newNoteTitle.value = title
    }

    private fun checkTitleExists(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _noteExists.value = notesInteractor.checkTitleExists(title)
        }
    }

    private fun getNoteId(title: String, noteText: String, createDateMS: Long): Int =
        title.hashCode() + noteText.hashCode() + createDateMS.hashCode() * 31
}
