package ru.coolnotes.di

import com.openkin.presentation.ui.addnote.AddNoteViewModel
import com.openkin.presentation.ui.archive.ArchiveViewModel
import com.openkin.presentation.ui.calendar.CalendarViewModel
import com.openkin.presentation.ui.editnote.EditNoteViewModel
import com.openkin.presentation.ui.notesboard.NotesViewModel
import com.openkin.presentation.ui.search.SearchViewModel
import com.openkin.presentation.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        NotesViewModel(
            notesInteractor = get(),
            settingsInteractor = get(),
            alarmScheduler = get()
        )
    }
    viewModel { ArchiveViewModel(notesInteractor = get()) }
    viewModel {
        AddNoteViewModel(
            notesInteractor = get(),
            settingsInteractor = get(),
            alarmScheduler = get(),
        )
    }
    viewModel {
        EditNoteViewModel(
            notesInteractor = get(),
            settingsInteractor = get(),
            alarmScheduler = get()
        )
    }
    viewModel { SearchViewModel(notesInteractor = get()) }
    viewModel { CalendarViewModel(notesInteractor = get(), settingsInteractor = get()) }
    viewModel { SettingsViewModel(notesInteractor = get()) }
}
