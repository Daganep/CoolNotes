package com.openkin.presentation.ui.addnote.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.openkin.presentation.ui.addnote.model.NotesColors

@Composable
fun AddNoteColorBar(
    onColorClicked: (NotesColors) -> Unit,
    modifier: Modifier,
) {
    var currentColor by remember { mutableStateOf(Color(0xFFFFE87A)) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NotesColors.entries.forEach { noteColor ->
                ColorBox(
                color = noteColor.startColor,
                isColorPicked = currentColor == noteColor.startColor,
                onBoxClicked = {
                    onColorClicked(noteColor)
                    currentColor = noteColor.startColor
                },
            )
        }
    }
}
