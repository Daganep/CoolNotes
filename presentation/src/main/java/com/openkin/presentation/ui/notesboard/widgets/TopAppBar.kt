package com.openkin.presentation.ui.notesboard.widgets

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
import com.openkin.domain.utils.WEIGHT_OF_HALF
import com.openkin.presentation.R
import com.openkin.presentation.ui.notesboard.model.SortType
import com.openkin.presentation.ui.notesboard.model.ViewType
import com.openkin.presentation.ui.theme.screenTitle

@Composable
fun TopAppBar(
    modifier: Modifier,
    onViewTypeClick: (ViewType) -> Unit,
    onSortClick: (SortType) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.notes_screen_appbar_title),
            style = MaterialTheme.typography.screenTitle,
            maxLines = SINGLE_LINE,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1F),
        )
        Row(
            modifier = Modifier.weight(WEIGHT_OF_HALF),
            horizontalArrangement = Arrangement.End,
        ) {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TopAppBarPreview() {
    TopAppBar(Modifier, {}, {})
}
