package ru.coolnotes.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.openkin.data.database.NotesDatabase
import com.openkin.data.database.notesDatabase
import com.openkin.data.repository.NoteRepository
import com.openkin.data.sharedprefs.ISharedPrefsStorage
import com.openkin.data.sharedprefs.SharedPrefsStorage
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.interactor.NotesInteractor
import com.openkin.domain.repository.INoteRepository
import com.openkin.domain.utils.COOL_NOTES_SHARED_PREFS
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.coolnotes.extensions.StateDataStore

val appModule = module {

    single<NotesDatabase> { notesDatabase(applicationContext = get()) }

    single<INoteRepository> { NoteRepository(database = get(), sharedPrefsStorage = get(), stateDataStore = get()) }

    single<INotesInteractor> { NotesInteractor(notesRepository = get()) }

    single<ISharedPrefsStorage> { SharedPrefsStorage(sharedPrefs = get()) }

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(COOL_NOTES_SHARED_PREFS, Context.MODE_PRIVATE)
    }

    single<DataStore<Preferences>> { androidApplication().StateDataStore }
}
