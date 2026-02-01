package ru.coolnotes.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.openkin.presentation.navigation.AppRouting
import com.openkin.presentation.navigation.Screen
import com.openkin.presentation.ui.settings.Settings
import com.openkin.presentation.ui.splash.Splash
import com.openkin.presentation.ui.addnote.AddNoteScreen
import com.openkin.presentation.ui.archive.ArchiveScreen
import com.openkin.presentation.ui.notesboard.NotesBoard
import ru.coolnotes.navigation.navigationbar.NavigationBar

@Composable
fun Navigation(scaffoldContentPaddings: PaddingValues) {
    val appRouting = AppRouting()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(Color.White)
    ) {
        val (currentScreen, bottomBar) = createRefs()
        var activeScreen by remember { mutableStateOf<Screen>(Screen.Splash) }
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
                    is Screen.NotesBoard -> {
                        activeScreen = Screen.NotesBoard
                        NavEntry(key = key, content = { NotesBoard(appRouting) })
                    }
                    is Screen.AddNote -> {
                        activeScreen = Screen.AddNote(key.noteId)
                        NavEntry(key = key, content = {
                            AddNoteScreen(appRouting, key.noteId, scaffoldContentPaddings)
                        })
                    }
                    is Screen.OpenNote -> NavEntry(key = key, content = {  })
                    is Screen.Calendar -> NavEntry(key = key, content = {  })
                    is Screen.Settings -> {
                        activeScreen = Screen.Settings
                        NavEntry(key = key, content = { Settings(appRouting::goBack) })
                    }
                    is Screen.Archive -> {
                        activeScreen = Screen.Archive
                        NavEntry(key = key, content = { ArchiveScreen(appRouting) })
                    }
                    is Screen.Search -> NavEntry(key = key, content = {  })
                    is Screen.Bin -> NavEntry(key = key, content = {  })
                }
            },
            modifier = Modifier
                .constrainAs(currentScreen) {
                    top.linkTo(anchor = parent.top)
                    bottom.linkTo(anchor = bottomBar.top)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
        )
        Box(
            modifier = Modifier
                .constrainAs(bottomBar) {
                    start.linkTo(anchor = parent.start, margin = 16.dp)
                    end.linkTo(anchor = parent.end, margin = 16.dp)
                    bottom.linkTo(anchor = parent.bottom, margin = 8.dp)
                    height = Dimension.value(56.dp)
                    width = Dimension.fillToConstraints
                }
        ) {
            if (activeScreen != Screen.Splash) NavigationBar(appRouting, activeScreen)
        }
    }
}
