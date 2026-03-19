package com.openkin.presentation.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import java.time.LocalDate

sealed class Screen : NavKey {

    data object Splash : Screen()

    data object NotesBoard : Screen()

    data class AddNote(val targetDate: LocalDate?) : Screen()

    data class EditNote(val noteId: Int) : Screen()

    data object Calendar : Screen()

    data object Settings : Screen()

    data object Archive : Screen()

    data object Search : Screen()
}

class AppRouting : IAppRouting {

    val backStack = mutableStateListOf<Screen>(Screen.Splash)

    override fun home() {
        backStack.clear()
        backStack.add(Screen.NotesBoard)
    }

    override fun addNote(targetDate: LocalDate?) {
        backStack.add(Screen.AddNote(targetDate))
    }

    override fun editNote(noteId: Int) {
        backStack.add(Screen.EditNote(noteId))
    }

    override fun openCalendar() {
        backStack.add(Screen.Calendar)
    }

    override fun openSettings() {
        backStack.add(Screen.Settings)
    }

    override fun openArchive() {
        backStack.add(Screen.Archive)
    }

    override fun openSearch() {
        backStack.add(Screen.Search)
    }

    override fun goBack() {
        backStack.removeLastOrNull()
    }
}
