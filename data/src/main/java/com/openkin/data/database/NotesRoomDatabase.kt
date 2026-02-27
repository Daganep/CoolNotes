package com.openkin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openkin.data.database.dao.NotesDao
import com.openkin.data.database.model.NoteDbo

/**
 * [NotesDatabase] — это класс обертка для того чтобы не прокидывать зависимость библиотеки Room
 * в модуль, в котором будет реализовано взаимодействие с базой данных
 */
class NotesDatabase internal constructor(private val database: NotesRoomDatabase) {
    val requestsDao: NotesDao
        get() = database.requestsDao()
}

@Database(entities = [NoteDbo::class], version = 1, exportSchema = false)
abstract class NotesRoomDatabase : RoomDatabase() {

    abstract fun requestsDao(): NotesDao
}

fun notesDatabase(applicationContext: Context): NotesDatabase {
    val database = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        NotesRoomDatabase::class.java, "notes_database"
    ).build()
    return NotesDatabase(database)
}
