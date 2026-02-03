package com.openkin.presentation.ui.archive.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.presentation.R
import com.openkin.presentation.ui.notesboard.SortType
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
            .height(56.dp),
    ) {
        Text(
            text = stringResource(R.string.archive_screen_appbar_title),
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier,
        )
        SortNotesButton(
            onSortClick = onSortClick,
            modifier = Modifier.padding(end = 8.dp),
        )
    }
}
