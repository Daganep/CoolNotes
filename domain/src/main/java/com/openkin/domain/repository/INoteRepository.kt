package com.openkin.domain.repository

import com.openkin.domain.model.NoteDto
import kotlinx.coroutines.flow.Flow

interface INoteRepository {
    suspend fun saveNote(note: NoteDto)
    suspend fun removeNote(noteId: Int)
    suspend fun getActualNotes(): Flow<List<NoteDto>>
    suspend fun getArchivedNotes(): Flow<List<NoteDto>>
    suspend fun getNote(noteId: Int): Flow<NoteDto?>
    suspend fun sendNoteToArchive(noteId: Int): Boolean
    suspend fun returnNoteToBoard(noteId: Int): Boolean
    suspend fun saveViewType(viewType: Int)
    suspend fun getStoredViewType(): Flow<Int>
    suspend fun checkTitleExists(noteTitle: String): Boolean
    suspend fun searchByText(query: String, searchByTitle: Boolean): Flow<List<NoteDto>>
    suspend fun getNotesByDateRange(startTime: Long, endTime: Long): Flow<List<NoteDto>>
    suspend fun getNotesCountForSelectedDate(daysList: List<Pair<Long, Long>>): Flow<List<Int>>
}
