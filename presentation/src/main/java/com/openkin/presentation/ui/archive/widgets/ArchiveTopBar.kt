package com.openkin.presentation.ui.archive.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.presentation.R
import com.openkin.presentation.ui.notesboard.model.SortType
import com.openkin.presentation.ui.notesboard.widgets.SortNotesButton

@Composable
fun ArchiveTopBar(
    onSortClick: (SortType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.archive_screen_appbar_title),
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1F),
        )
        SortNotesButton(
            onSortClick = onSortClick,
            modifier = Modifier.padding(end = 8.dp).weight(1F),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ArchiveTopBarPreview() {
    ArchiveTopBar(onSortClick = {}, modifier = Modifier.padding(horizontal = 16.dp))
}
