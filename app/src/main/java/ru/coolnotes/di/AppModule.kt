package ru.coolnotes.di

import com.openkin.data.database.NotesDatabase
import com.openkin.data.database.notesDatabase
import com.openkin.data.repository.NoteRepository
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.interactor.NotesInteractor
import com.openkin.domain.repository.INoteRepository
import org.koin.dsl.module

val appModule = module {

    single<NotesDatabase> { notesDatabase(applicationContext = get()) }

    single<INoteRepository> { NoteRepository(database = get()) }

    single<INotesInteractor> { NotesInteractor(notesRepository = get()) }
}
