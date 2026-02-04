package com.openkin.presentation.ui.addnote.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.openkin.presentation.ui.addnote.model.NotesColors

@Composable
fun AddNoteColorBar(
    onColorClicked: (NotesColors) -> Unit,
    currentColor: Color,
    modifier: Modifier,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NotesColors.entries.forEach { noteColor ->
                ColorBox(
                    color = noteColor.startColor,
                    isColorPicked = currentColor == noteColor.startColor,
                    onBoxClicked = { onColorClicked(noteColor) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddNoteColorBarPreview() {
    AddNoteColorBar(
        onColorClicked = {},
        currentColor = Color(0xFFFD8484),
        modifier = Modifier,
    )
}
