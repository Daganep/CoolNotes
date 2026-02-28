package com.openkin.presentation.ui.archive.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.presentation.R

@Composable
fun ClearArchiveButtonButton(
    modifier: Modifier,
    onClearClick: () -> Unit,
) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.image_note_to_bin),
            contentDescription = stringResource(R.string.archive_screen_clear_archive_button),
            modifier = modifier
                .padding(start = 24.dp)
                .size(24.dp)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = { onClearClick() }
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ClearArchiveButtonPreview() {
    ClearArchiveButtonButton(Modifier) {}
}
