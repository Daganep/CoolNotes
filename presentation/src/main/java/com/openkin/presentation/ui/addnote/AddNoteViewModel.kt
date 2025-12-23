package com.openkin.presentation.ui.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.model.NoteUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNoteViewModel(
    private val notesInteractor: INotesInteractor,
) : ViewModel() {

    fun saveNote(noteTitle: String, noteText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val sdf = SimpleDateFormat("dd.M.yyyy hh:mm:ss", Locale.ROOT)
            val currentDate = sdf.format(Date())
            notesInteractor.saveNote(
                NoteUi(
                    title = noteTitle,
                    description = noteText,
                    createDate = currentDate,
                )
            )
        }
    }
}
