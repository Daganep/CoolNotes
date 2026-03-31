package com.openkin.presentation.ui.archive.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.domain.utils.SINGLE_LINE
import com.openkin.presentation.R
import com.openkin.presentation.ui.notesboard.model.SortType
import com.openkin.presentation.ui.notesboard.widgets.SortNotesButton
import com.openkin.presentation.ui.theme.screenTitle

@Composable
fun ArchiveTopBar(
    hasArchivedNotes: Boolean,
    onSortClick: (SortType) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.archive_screen_appbar_title),
            style = MaterialTheme.typography.screenTitle,
            maxLines = SINGLE_LINE,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1F),
        )
        Row(
            modifier = Modifier.weight(0.5F).padding(end = 8.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            SortNotesButton(
                onSortClick = onSortClick,
                modifier = Modifier,
            )
            if (hasArchivedNotes) {
                ClearArchiveButtonButton(
                    modifier = Modifier,
                    onClearClick = onClearClick,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ArchiveTopBarWithClearPreview() {
    ArchiveTopBar(
        hasArchivedNotes = true,
        onSortClick = {},
        onClearClick = {},
        modifier = Modifier.padding(horizontal = 16.dp),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ArchiveTopBarWithoutClearPreview() {
    ArchiveTopBar(
        hasArchivedNotes = false,
        onSortClick = {},
        onClearClick = {},
        modifier = Modifier.padding(horizontal = 16.dp),
    )
}
