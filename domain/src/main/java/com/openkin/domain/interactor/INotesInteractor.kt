package com.openkin.domain.interactor

import com.openkin.domain.model.NoteDto
import com.openkin.domain.model.NoteUi
import kotlinx.coroutines.flow.Flow

interface INotesInteractor {
    suspend fun saveNote(note: NoteUi)
    suspend fun removeNote(note: NoteUi)
    suspend fun getActualNotes(): Flow<List<NoteUi>>
    suspend fun getArchivedNotes(): Flow<List<NoteUi>>
    suspend fun getNote(noteId: Int): Flow<NoteUi?>
    suspend fun sendNoteToArchive(noteId: Int): Boolean
}
