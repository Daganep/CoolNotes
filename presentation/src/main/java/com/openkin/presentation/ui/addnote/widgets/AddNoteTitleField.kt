package com.openkin.presentation.ui.addnote.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.domain.utils.EMPTY_STRING
import com.openkin.domain.utils.NOTE_TITLE_MAX_LENGTH
import com.openkin.domain.utils.SHORT_TEXT_EXAMPLE
import com.openkin.presentation.R

@Composable
fun AddNoteTitleField(
    noteTitle: String,
    isNoteTitleExists: Boolean,
    onNoteTitleChanged: (String) -> Unit,
    modifier: Modifier,
) {
    var isError by remember { mutableStateOf(false) }
    var errorMessage = EMPTY_STRING
    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = noteTitle,
            onValueChange = { onNoteTitleChanged(it) },
            label = { Text(text = stringResource(R.string.add_note_screen_new_title)) },
            isError = isError,
            singleLine = true,
            trailingIcon = {
                if (noteTitle.isNotEmpty()) {
                    ClearFieldButton(onNoteTitleChanged = onNoteTitleChanged)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (noteTitle.length > NOTE_TITLE_MAX_LENGTH) {
            isError = true
            errorMessage = stringResource(
                R.string.add_note_error_title_length,
                NOTE_TITLE_MAX_LENGTH,
            )
        } else if (isNoteTitleExists) {
            isError = true
            errorMessage = stringResource(
                R.string.add_note_error_title_exists,
                NOTE_TITLE_MAX_LENGTH,
            )
        } else isError = false

        if (isError) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = errorMessage,
                color = Color.Red,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddNoteTitleFieldPreview() {
    AddNoteTitleField(
        noteTitle = SHORT_TEXT_EXAMPLE,
        isNoteTitleExists = false,
        onNoteTitleChanged = {},
        modifier = Modifier,
    )
}
