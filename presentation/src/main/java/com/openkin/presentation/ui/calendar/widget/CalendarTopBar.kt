package com.openkin.presentation.ui.calendar.widget

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
import com.openkin.presentation.R
import java.time.LocalDate

@Composable
fun CalendarTopBar(
    onTodayClick: () -> Unit,
    today: LocalDate,
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
            text = stringResource(R.string.calendar_screen_top_bar_title),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier,
        )
        CurrentDayButton(
            today = today,
            modifier = Modifier.padding(end = 8.dp, bottom = 4.dp),
            onClick = { onTodayClick() },
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CalendarTopBarPreview() {
    CalendarTopBar(
        onTodayClick = {},
        today = LocalDate.now(),
        modifier = Modifier.padding(horizontal = 16.dp),
    )
}
