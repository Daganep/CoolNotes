package com.openkin.presentation.ui.calendar.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.domain.utils.CALENDAR_NOTES_COUNTER_MAX_VALUE
import com.openkin.presentation.R

@Composable
fun NotesCounter(
    notesCount: Int,
    modifier: Modifier,
) {
    val counter = if (notesCount < CALENDAR_NOTES_COUNTER_MAX_VALUE) notesCount.toString()
    else stringResource(R.string.calendar_screen_counter_more_then_10)
    Box(
        modifier = modifier.size(23.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_notes_count_one),
            contentDescription = stringResource(R.string.calendar_screen_notes_on_that_day),
            modifier = Modifier.size(25.dp),
        )
        Text(
            text = counter,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotesCounterLessThenTenPreview() {
    NotesCounter(5, Modifier)
}

@Preview(showBackground = true)
@Composable
fun NotesCounterMoreThenTenPreview() {
    NotesCounter(15, Modifier)
}
