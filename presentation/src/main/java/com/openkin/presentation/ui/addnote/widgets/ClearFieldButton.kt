package com.openkin.presentation.ui.addnote.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.domain.utils.EMPTY_STRING
import com.openkin.presentation.R

@Composable
fun ClearFieldButton(
    onNoteTitleChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.icon_clear_edit_text),
        contentDescription =
            stringResource(R.string.add_note_screen_clear_field_button),
        modifier = Modifier
            .size(28.dp)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { onNoteTitleChanged(EMPTY_STRING) }
            ),
    )
}

@Preview(showBackground = true)
@Composable
fun ClearFieldButtonPreview() {
    ClearFieldButton(onNoteTitleChanged = {})
}
