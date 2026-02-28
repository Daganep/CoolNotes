package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.openkin.domain.model.NoteUi
import com.openkin.presentation.R

@Composable
fun NotesCommonList(
    notesList: List<NoteUi>,
    swipedNote: Pair<Int, Int>,
    onNoteSwiped: (Int, Int) -> Unit,
    onNoteClick: (Int) -> Unit,
    onArchiveClicked: (NoteUi) -> Unit,
    isDetailedList: Boolean,
    listState: LazyListState,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 8.dp),
        state = listState,
    ) {
        itemsIndexed(items = notesList, key = { _, item -> item.id }) { index, item ->
            SwipeableNote(
                isRevealed = item.id == swipedNote.first,
                actions = {
                    ActionOnSwipe(
                        onClick = {
                            onNoteSwiped(0, -1)
                            onArchiveClicked(item)
                        },
                        drawableId = R.drawable.image_put_to_archive,
                        contentDescriptionId = R.string.notes_board_replace_to_archive_button,
                        modifier = Modifier,
                    )
                },
                onExpanded = { onNoteSwiped(item.id, index) },
                onCollapsed = { onNoteSwiped(0, -1) },
                modifier = Modifier.animateItem(),
            ) {
                if(!isDetailedList) HorizontalSimpleNote(item, onNoteClick)
                else HorizontalDetailsNote(item, onNoteClick)
            }
        }
    }
}
