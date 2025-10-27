package ru.coolnotes.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.openkin.presentation.navigation.AppRouting
import com.openkin.presentation.navigation.Screen
import com.openkin.presentation.ui.notesboard.NotesBoard
import com.openkin.presentation.ui.Settings
import com.openkin.presentation.ui.Splash

@Composable
fun Navigation() {
    val appRouting = AppRouting()
    NavDisplay(
        backStack = appRouting.backStack,
        onBack = { appRouting.goBack() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = { key ->
            when(key) {
                is Screen.Splash -> NavEntry(key = key, content = { Splash(appRouting::home) })
                is Screen.NotesBoard -> NavEntry(key = key, content = { NotesBoard(appRouting) })
                is Screen.AddNote -> NavEntry(key = key, content = {  })
                is Screen.OpenNote -> NavEntry(key = key, content = {  })
                is Screen.Calendar -> NavEntry(key = key, content = {  })
                is Screen.Settings -> NavEntry(key = key, content = { Settings(appRouting::goBack) })
                is Screen.Archive -> NavEntry(key = key, content = {  })
                is Screen.Bin -> NavEntry(key = key, content = {  })
                else -> NavEntry(key = key, content = {  })
            }
        }
    )
}
