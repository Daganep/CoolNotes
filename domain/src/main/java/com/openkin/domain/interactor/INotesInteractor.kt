package com.openkin.domain.interactor

import com.openkin.domain.model.NoteUi
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface INotesInteractor {
    suspend fun saveNote(note: NoteUi)
    suspend fun removeNote(noteId: Int)
    suspend fun removeAllArchivedNotes()
    suspend fun getActualNotes(): Flow<List<NoteUi>>
    suspend fun getArchivedNotes(): Flow<List<NoteUi>>
    suspend fun getNote(noteId: Int): Flow<NoteUi?>
    suspend fun sendNoteToArchive(noteId: Int): Boolean
    suspend fun returnNoteToBoard(noteId: Int): Boolean
    suspend fun saveViewType(viewType: Int)
    suspend fun getStoredViewType(): Flow<Int>
    suspend fun checkTitleExists(noteTitle: String): Boolean
    suspend fun searchByText(query: String, searchByTitle: Boolean): Flow<List<NoteUi>>
    suspend fun getNotesByDay(day: LocalDate): Flow<List<NoteUi>>
    suspend fun getNotesCountForSelectedDate(daysList: List<LocalDate>): Flow<List<Int>>
    suspend fun getSelectedDay(): Flow<LocalDate?>
}
