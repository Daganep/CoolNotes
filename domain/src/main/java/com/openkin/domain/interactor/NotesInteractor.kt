package com.openkin.domain.interactor

import com.openkin.domain.mapper.toNoteDto
import com.openkin.domain.mapper.toNoteUi
import com.openkin.domain.model.NoteUi
import com.openkin.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class NotesInteractor(
    private val notesRepository: INoteRepository,
) : INotesInteractor {

    override suspend fun saveNote(note: NoteUi) {
        notesRepository.saveNote(note.toNoteDto())
    }

    override suspend fun removeNote(noteId: Int) {
        notesRepository.removeNote(noteId)
    }

    override suspend fun removeAllArchivedNotes() {
        notesRepository.removeAllArchivedNotes()
    }

    override suspend fun getActualNotes(): Flow<List<NoteUi>> {
        return notesRepository.getActualNotes().map { notesDto ->
            notesDto.map { it.toNoteUi() }
        }
    }

    override suspend fun getArchivedNotes(): Flow<List<NoteUi>> {
        return notesRepository.getArchivedNotes().map { notesDto ->
            notesDto.map { it.toNoteUi() }
        }
    }

    override suspend fun getNote(noteId: Int): Flow<NoteUi?> {
        return notesRepository.getNote(noteId).map { it?.toNoteUi() }
    }

    override suspend fun sendNoteToArchive(noteId: Int) = notesRepository.sendNoteToArchive(noteId)

    override suspend fun returnNoteToBoard(noteId: Int): Boolean =
        notesRepository.returnNoteToBoard(noteId)

    override suspend fun saveViewType(viewType: Int) {
        notesRepository.saveViewType(viewType)
    }

    override suspend fun getStoredViewType(): Flow<Int> =
        notesRepository.getStoredViewType()

    override suspend fun checkTitleExists(noteTitle: String): Boolean =
        notesRepository.checkTitleExists(noteTitle)

    override suspend fun searchByText(query: String, searchByTitle: Boolean): Flow<List<NoteUi>> =
        notesRepository.searchByText(query, searchByTitle).map { notesDto ->
            notesDto.map { it.toNoteUi() }
        }

    override suspend fun getNotesByDay(day: LocalDate): Flow<List<NoteUi>> =
        notesRepository.getNotesByDay(day).map { notesDto ->
            notesDto.map { it.toNoteUi() }
        }

    override suspend fun getNotesCountForSelectedDate(daysList: List<LocalDate>): Flow<List<Int>> {
        return notesRepository.getNotesCountForSelectedDate(daysList)
    }

    override suspend fun getSelectedDay(): Flow<LocalDate?> =
        notesRepository.getSelectedDay()
}
