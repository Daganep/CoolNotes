package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.openkin.domain.model.NoteUi
import com.openkin.domain.utils.BIG_BLOCKS_COLUMN_COUNT

@Composable
fun NotesBlocks(
    notesList: List<NoteUi>,
    onNoteClick: (Int) -> Unit,
    columnCount: Int,
    gridState: LazyGridState,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = gridState,
    ) {
        items(items = notesList, key = { it.id }) { item ->
            if (columnCount == BIG_BLOCKS_COLUMN_COUNT) {
                BigSquareNote(
                    note = item,
                    onNoteClick = onNoteClick,
                    modifier = Modifier.animateItem(),
                )
            } else {
                SmallSquareNote(
                    note = item,
                    onNoteClick = onNoteClick,
                    modifier = Modifier.animateItem(),
                )
            }

        }
    }
}
