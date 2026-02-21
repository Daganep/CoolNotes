package com.openkin.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.openkin.data.database.NotesDatabase
import com.openkin.data.datastorekeys.CALENDAR_SELECTED_DAY
import com.openkin.data.datastorekeys.LAST_SELECTED_VIEW_TYPE
import com.openkin.data.mapper.toNoteDbo
import com.openkin.data.mapper.toNoteDto
import com.openkin.data.utils.updateQueryForSearchSubstring
import com.openkin.domain.model.NoteDto
import com.openkin.domain.repository.INoteRepository
import com.openkin.domain.utils.DEFAULT_VIEW_TYPE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneOffset

class NoteRepository(
    private val database: NotesDatabase,
    private val stateDataStore: DataStore<Preferences>,
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

    override suspend fun saveViewType(viewType: Int) {
        stateDataStore.edit { state -> state[LAST_SELECTED_VIEW_TYPE] = viewType }
    }

    override suspend fun getStoredViewType(): Flow<Int> =
        stateDataStore.data.map { it[LAST_SELECTED_VIEW_TYPE] ?: DEFAULT_VIEW_TYPE }

    override suspend fun checkTitleExists(noteTitle: String): Boolean =
        database.requestsDao.getNoteWithTitle(noteTitle) != null

    override suspend fun searchByText(query: String, searchByTitle: Boolean): Flow<List<NoteDto>> {
        val searchResult = if (searchByTitle) {
            database.requestsDao.searchByTitle(updateQueryForSearchSubstring(query))
        } else {
            database.requestsDao.searchByDescription(updateQueryForSearchSubstring(query))
        }
        return searchResult
            .catch {
                //TODO обработать ошибку
            }.map { result ->
                result.map { noteDbo -> noteDbo.toNoteDto() }
            }
    }

    override suspend fun getNotesByDateRange(day: LocalDate): Flow<List<NoteDto>> {
        storeSelectedDay(day)
        val startTime = day.atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        val endTime = day.plusDays(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        return database.requestsDao.getNotesByDateRange(startTime, endTime)
            .catch {
                //TODO обработать ошибку
            }.map { result ->
                result.map { noteDbo -> noteDbo.toNoteDto() }
            }
    }

    override suspend fun getNotesCountForSelectedDate(
        daysList: List<Pair<Long, Long>>
    ): Flow<List<Int>> {
        val resultList = mutableListOf<Int>()
        daysList.forEach { day ->
            resultList.add(database.requestsDao.getNotesCountByDateRange(day.first, day.second))
        }
        return flowOf(resultList.toList())
    }

    override suspend fun getSelectedDay(): Flow<LocalDate?> =
        stateDataStore.data.map { state ->
            val storedDay = state[CALENDAR_SELECTED_DAY]
            val selectedDay = if (storedDay != null) {
                val (year, month, day) = storedDay.split(";")
                LocalDate.of(year.toInt(), month.toInt(), day.toInt())
            } else null
            return@map selectedDay
        }

    private suspend fun storeSelectedDay(day: LocalDate) {
        val selectedDay = "${day.year};${day.month.value};${day.dayOfMonth}"
        stateDataStore.edit { state -> state[CALENDAR_SELECTED_DAY] = selectedDay }
    }
}
