package com.openkin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openkin.data.database.model.NoteDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM table_notes_database")
    fun getAll() : Flow<List<NoteDbo>>

    @Query("SELECT * FROM table_notes_database WHERE archived = 0")
    fun getAllActualNotes() : Flow<List<NoteDbo>>

    @Query("SELECT * FROM table_notes_database WHERE archived = 1")
    fun getAllArchivedNotes() : Flow<List<NoteDbo>>

    @Query("SELECT * FROM table_notes_database WHERE id = :id")
    suspend fun getNote(id: Int) : NoteDbo?

    @Query("SELECT * FROM table_notes_database WHERE title = :title")
    suspend fun getNoteWithTitle(title: String) : NoteDbo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(request: NoteDbo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(requests: List<NoteDbo>)

    @Query("DELETE FROM table_notes_database WHERE id = :noteId")
    suspend fun remove(noteId: Int)

    @Query("DELETE FROM table_notes_database")
    suspend fun removeAll()

    @Query("SELECT COUNT(*) FROM table_notes_database")
    suspend fun getSize(): Int

    @Query("DELETE FROM table_notes_database")
    suspend fun clearDatabase()

    @Query("SELECT * FROM table_notes_database WHERE title LIKE :search ESCAPE '@'")
    fun searchByTitle(search: String): Flow<List<NoteDbo>>

    @Query("SELECT * FROM table_notes_database WHERE description LIKE :search ESCAPE '@'")
    fun searchByDescription(search: String): Flow<List<NoteDbo>>

    @Query("SELECT * FROM table_notes_database WHERE (createDateMS BETWEEN :startTime AND :endTime) AND archived = 0")
    fun getNotesByDateRange(startTime: Long, endTime: Long): Flow<List<NoteDbo>>

    @Query("SELECT COUNT(createDateMS) FROM table_notes_database WHERE (createDateMS BETWEEN :startTime AND :endTime) AND archived = 0")
    suspend fun getNotesCountByDateRange(startTime: Long, endTime: Long): Int
}
