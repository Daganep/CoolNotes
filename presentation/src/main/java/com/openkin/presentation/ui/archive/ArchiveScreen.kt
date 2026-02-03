package com.openkin.presentation.ui.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.notesboard.widgets.ActionOnSwipe
import com.openkin.presentation.ui.notesboard.widgets.HorizontalSimpleNote
import com.openkin.presentation.ui.notesboard.widgets.SwipeableNote
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArchiveScreen(routing: IAppRouting) {
    ArchiveScreen(
        viewModel = koinViewModel(),
        routing = routing,
    )
}

@Composable
fun ArchiveScreen(viewModel: ArchiveViewModel, routing: IAppRouting) {
    viewModel.getArchive()
    val archivedNotes by viewModel.notesState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(Color.White)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.archive_screen_appbar_title),
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier,
            )
        }
        if (archivedNotes.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.archive_screen_empty_list),
                    fontSize = 18.sp,
                )
            }
        } else {
            var swipedNote by remember { mutableIntStateOf(0) }
            LazyColumn(
                modifier = Modifier,
                contentPadding = PaddingValues(bottom = 8.dp, top = 8.dp),
            ) {
                items(items = archivedNotes, key = { it.id }) { item ->
                    SwipeableNote(
                        isRevealed = item.id == swipedNote,
                        actions = {
                            ActionOnSwipe(
                                onClick = {
                                    swipedNote = 0
                                    viewModel.returnNoteToBoard(item.id)
                                },
                                drawableId = R.drawable.image_back_to_board_arrow,
                                contentDescriptionId = R.string.archive_screen_delete_archive_button,
                                size = 52.dp,
                                modifier = Modifier,
                            )

                            ActionOnSwipe(
                                onClick = {
                                    swipedNote = 0
                                    viewModel.removeNote(item.id)
                                },
                                drawableId = R.drawable.image_note_to_bin,
                                contentDescriptionId = R.string.archive_screen_delete_archive_button,
                                modifier = Modifier,
                            )
                        },
                        onExpanded = { swipedNote = item.id },
                        onCollapsed = { swipedNote = 0 }
                    ) {
                        HorizontalSimpleNote(item, routing::addNote)
                    }
                }
            }
        }
    }
}
