package com.openkin.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openkin.data.database.model.NoteDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM table_notes_database")
    fun getAll() : Flow<List<NoteDbo>>

    @Query("SELECT * FROM table_notes_database")
    suspend fun getAllNotes() : List<NoteDbo>

    @Query("SELECT * FROM table_notes_database WHERE archived = 0")
    suspend fun getAllActualNotes() : List<NoteDbo>

    @Query("SELECT * FROM table_notes_database WHERE archived = 1")
    suspend fun getAllArchivedNotes() : List<NoteDbo>

    @Query("SELECT * FROM table_notes_database " +
            "WHERE title = :title " +
            "and description = :description " +
            "and createDate = :createDate"
    ) suspend fun getNote(
        title: String,
        description: String,
        createDate: String,
    ) : NoteDbo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(request: NoteDbo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(requests: List<NoteDbo>)

    @Delete
    suspend fun remove(request: NoteDbo)

    @Query("DELETE FROM table_notes_database")
    suspend fun removeAll()

    @Query("SELECT COUNT(*) FROM table_notes_database")
    suspend fun getSize(): Int

    @Query("DELETE FROM table_notes_database")
    suspend fun clearDatabase()
}
