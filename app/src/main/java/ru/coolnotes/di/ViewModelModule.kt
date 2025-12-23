package ru.coolnotes.di

import com.openkin.presentation.ui.addnote.AddNoteViewModel
import com.openkin.presentation.ui.notesboard.NotesViewModel
import com.openkin.presentation.ui.archive.ArchiveViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NotesViewModel(notesInteractor = get()) }
    viewModel { ArchiveViewModel() }
    viewModel { AddNoteViewModel(notesInteractor = get()) }
}
