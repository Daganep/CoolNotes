package com.openkin.data.repository

import com.openkin.data.database.NotesDatabase
import com.openkin.data.mapper.toNoteDbo
import com.openkin.data.mapper.toNoteDto
import com.openkin.domain.model.NoteDto
import com.openkin.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class NoteRepository(
    private val database: NotesDatabase,
) : INoteRepository {

    override suspend fun saveNote(note: NoteDto) {
        database.requestsDao.insert(note.toNoteDbo())
    }

    override suspend fun removeNote(noteId: Int) {
        database.requestsDao.remove(noteId)
    }

    override suspend fun getActualNotes(): Flow<List<NoteDto>> {
        return database.requestsDao.getAllActualNotes()
            .catch {
                //TODO обработать ошибку
            }.map { result ->
                result.map { noteDbo -> noteDbo.toNoteDto() }
            }
    }

    override suspend fun getArchivedNotes(): Flow<List<NoteDto>> {
        return database.requestsDao.getAllArchivedNotes()
            .catch {
                //TODO обработать ошибку
            }.map { result ->
                result.map { noteDbo -> noteDbo.toNoteDto() }
            }
    }

    override suspend fun getNote(noteId: Int): Flow<NoteDto?> {
        return flowOf(database.requestsDao.getNote(noteId)?.toNoteDto())
    }

    override suspend fun sendNoteToArchive(noteId: Int): Boolean {
        val note = database.requestsDao.getNote(noteId)
        return if (note != null) {
            val updatedNote = note.copy(archived = true)
            database.requestsDao.insert(updatedNote)
            true
        } else false
    }

    override suspend fun returnNoteToBoard(noteId: Int): Boolean {
        val note = database.requestsDao.getNote(noteId)
        return if (note != null) {
            val updatedNote = note.copy(archived = false)
            database.requestsDao.insert(updatedNote)
            true
        } else false
    }
}
