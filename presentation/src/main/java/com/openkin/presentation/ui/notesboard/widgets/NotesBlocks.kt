package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.openkin.domain.model.NoteUi

@Composable
fun NotesBlocks(
    notesList: List<NoteUi>,
    onNoteClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items = notesList, key = { it.id }) { item ->
            SquareNote(
                note = item,
                onNoteClick = onNoteClick,
                onMenuClick = {},
            )
        }
    }
}