package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.presentation.R

@Composable
fun AddNoteFloatButton(
    onClick: () -> Unit,
    modifier: Modifier,
) {
    Button(
        shape = CircleShape,
        contentPadding = PaddingValues(8.dp),
        colors = ButtonColors(
            containerColor = Color(0x07000000),
            disabledContainerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContentColor = Color.Transparent,
        ),
        onClick = { onClick() },
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_add_new_note),
            contentDescription = stringResource(R.string.notes_board_add_note_button),
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterVertically),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddNoteFloatButtonPreview() {
    AddNoteFloatButton(onClick = {}, modifier = Modifier)
}
