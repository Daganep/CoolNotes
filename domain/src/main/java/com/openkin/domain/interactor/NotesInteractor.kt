package com.openkin.domain.interactor

import com.openkin.domain.mapper.toNoteDto
import com.openkin.domain.mapper.toNoteUi
import com.openkin.domain.model.NoteDto
import com.openkin.domain.model.NoteUi
import com.openkin.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesInteractor(
    private val notesRepository: INoteRepository,
) : INotesInteractor {

    override suspend fun saveNote(note: NoteUi) {
        notesRepository.saveNote(note.toNoteDto())
    }

    override suspend fun removeNote(note: NoteUi) {
        notesRepository.removeNote(note.toNoteDto())
    }

    override suspend fun getActualNotes(): Flow<List<NoteUi>> {
        return notesRepository.getActualNotes().map { notesDto ->
            notesDto.map { it.toNoteUi() }
        }
    }

    override suspend fun getArchivedNotes(): Flow<List<NoteUi>> {
        return notesRepository.getActualNotes().map { notesDto ->
            notesDto.map { it.toNoteUi() }
        }
    }

    override suspend fun getNote(noteId: Int): Flow<NoteUi?> {
        return notesRepository.getNote(noteId).map { it?.toNoteUi() }
    }

    override suspend fun sendNoteToArchive(noteId: Int) = notesRepository.sendNoteToArchive(noteId)
}
