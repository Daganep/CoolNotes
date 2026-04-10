package ru.coolnotes.navigation.navigationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.openkin.presentation.R
import com.openkin.presentation.navigation.AppRouting
import com.openkin.presentation.navigation.Screen

@Composable
fun NavigationBar(
    routing: AppRouting,
    activeScreen: Screen,
    onThemeLightChanged: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 8.dp)
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        NavigationButton(
            modifier = Modifier,
            imageId = R.drawable.image_notes,
            descriptionId = R.string.navigation_bar_notes,
            isActive = activeScreen == Screen.NotesBoard,
            onClick = routing::home,
        )
        NavigationButton(
            modifier = Modifier,
            imageId = R.drawable.image_calendar,
            descriptionId = R.string.navigation_bar_calendar,
            isActive = activeScreen == Screen.Calendar,
            onClick = routing::openCalendar,
        )
        NavigationButton(
            modifier = Modifier,
            imageId = R.drawable.image_search,
            descriptionId = R.string.navigation_bar_search,
            isActive = activeScreen == Screen.Search,
            onClick = routing::openSearch,
        )
        NavigationButton(
            modifier = Modifier,
            imageId = R.drawable.image_archive,
            descriptionId = R.string.navigation_bar_archive,
            isActive = activeScreen == Screen.Archive,
            onClick = routing::openArchive,
        )
        NavigationButton(
            modifier = Modifier,
            imageId = R.drawable.image_bottom_menu,
            descriptionId = R.string.navigation_bar_settings,
            isActive = activeScreen is Screen.Settings,
            onClick = { routing.openSettings(onThemeLightChanged) },
        )
    }
}
