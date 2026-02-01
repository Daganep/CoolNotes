package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.openkin.presentation.R
import com.openkin.presentation.ui.notesboard.SortType
import com.openkin.presentation.ui.notesboard.ViewType

@Composable
fun TopAppBar(
    modifier: Modifier,
    onViewTypeClick: (ViewType) -> Unit,
    onSortClick: (SortType) -> Unit,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.notes_screen_appbar_title),
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier,
        )
        Row {
            SortNotesButton(
                modifier = Modifier,
                onSortClick = onSortClick,
            )
            ViewTypesButton(
                modifier = Modifier,
                onViewTypeClick = onViewTypeClick,
            )
        }
    }
}