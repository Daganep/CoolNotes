package ru.coolnotes.di

import android.app.AlarmManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.openkin.data.database.NotesDatabase
import com.openkin.data.database.notesDatabase
import com.openkin.data.repository.NoteRepository
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.interactor.NotesInteractor
import com.openkin.domain.repository.INoteRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.coolnotes.extensions.StateDataStore

val appModule = module {

    single<NotesDatabase> { notesDatabase(applicationContext = get()) }

    single<INoteRepository> { NoteRepository(database = get(), stateDataStore = get()) }

    single<INotesInteractor> { NotesInteractor(notesRepository = get()) }

    single<DataStore<Preferences>> { androidApplication().StateDataStore }

    single<AlarmManager> {
        androidApplication().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
}
