package ru.coolnotes.di

import com.openkin.presentation.ui.notesboard.NotesBoardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { NotesBoardViewModel() }
}
