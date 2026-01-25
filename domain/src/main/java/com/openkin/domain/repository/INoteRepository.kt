package com.openkin.domain.repository

import com.openkin.domain.model.NoteDto
import kotlinx.coroutines.flow.Flow

interface INoteRepository {
    suspend fun saveNote(note: NoteDto)
    suspend fun removeNote(note: NoteDto)
    suspend fun getActualNotes(): Flow<List<NoteDto>>
    suspend fun getArchivedNotes(): Flow<List<NoteDto>>
    suspend fun getNote(noteId: Int): Flow<NoteDto?>
}
