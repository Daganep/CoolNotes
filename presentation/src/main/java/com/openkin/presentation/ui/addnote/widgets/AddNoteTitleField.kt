package com.openkin.presentation.ui.addnote.widgets

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.openkin.domain.utils.SHORT_TEXT_EXAMPLE
import com.openkin.presentation.R

@Composable
fun AddNoteTitleField(
    noteTitle: String,
    onNoteTitleChanged: (String) -> Unit,
    modifier: Modifier,
) {
    OutlinedTextField(
        value = noteTitle,
        onValueChange = { onNoteTitleChanged(it) },
        label = { Text(text = stringResource(R.string.add_note_screen_new_title)) },
        singleLine = true,
        trailingIcon = {
            if (noteTitle.isNotEmpty()) {
                ClearFieldButton(onNoteTitleChanged = onNoteTitleChanged)
            }
        },
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun AddNoteTitleFieldPreview() {
    AddNoteTitleField(
        noteTitle = SHORT_TEXT_EXAMPLE,
        onNoteTitleChanged = {},
        modifier = Modifier,
    )
}
