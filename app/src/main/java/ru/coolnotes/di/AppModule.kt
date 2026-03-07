package ru.coolnotes.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.openkin.data.database.NotesDatabase
import com.openkin.data.database.notesDatabase
import com.openkin.data.repository.NoteRepository
import com.openkin.data.repository.SettingsRepository
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.interactor.ISettingsInteractor
import com.openkin.domain.interactor.NotesInteractor
import com.openkin.domain.interactor.SettingsInteractor
import com.openkin.domain.repository.INoteRepository
import com.openkin.domain.repository.ISettingsRepository
import com.openkin.domain.utils.COOL_NOTES_SETTINGS_PREFS
import com.openkin.domain.utils.COOL_NOTES_STATE_PREFS
import com.openkin.presentation.utils.AlarmScheduler
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.coolnotes.broadcastreceiver.NotificationAlarmScheduler
import ru.coolnotes.extensions.SettingsDataStore
import ru.coolnotes.extensions.StateDataStore

val appModule = module {

    single<NotesDatabase> { notesDatabase(applicationContext = get()) }

    single<INoteRepository> {
        NoteRepository(database = get(), stateDataStore = get(named(COOL_NOTES_STATE_PREFS)),)
    }

    single<ISettingsRepository> {
        SettingsRepository(
            stateDataStore = get(named(COOL_NOTES_STATE_PREFS)),
            settingsDataStore = get(named(COOL_NOTES_SETTINGS_PREFS)),
        )
    }

    single<INotesInteractor> { NotesInteractor(notesRepository = get()) }

    single<ISettingsInteractor> { SettingsInteractor(settingsRepository = get()) }

    single<DataStore<Preferences>>(named(COOL_NOTES_STATE_PREFS)) {
        androidApplication().StateDataStore
    }

    single<DataStore<Preferences>>(named(COOL_NOTES_SETTINGS_PREFS)) {
        androidApplication().SettingsDataStore
    }

    single<AlarmScheduler> { NotificationAlarmScheduler(context = get()) }
}
