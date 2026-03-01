package com.openkin.presentation.ui.calendar.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.presentation.ui.theme.lightGray
import com.openkin.presentation.ui.theme.lightYellow
import com.openkin.presentation.ui.theme.red
import com.openkin.presentation.ui.theme.white
import com.openkin.presentation.utils.toSp
import java.time.LocalDate

@Composable
fun Day(
    modifier: Modifier,
    day: LocalDate,
    isToday: Boolean,
    isDaySelected: Boolean,
    inSelectedMonth: Boolean,
    notesCount: Int = 0,
    onDayClicked: (LocalDate) -> Unit,
) {
    val density = LocalDensity.current
    val elevation = if (inSelectedMonth) 3.dp else 2.dp
    val border = if (isDaySelected) {
        BorderStroke(1.dp, red)
    } else if(inSelectedMonth) {
        BorderStroke(1.dp, lightGray)
    } else null
    val background = if (isToday) lightYellow else white
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(elevation),
        border = border,
        modifier = modifier
            .height(45.dp)
            .clickable { onDayClicked(day) },
    ) {
        Box(
            modifier
                .fillMaxSize()
                .background(background),
        ) {
            Text(
                text = day.dayOfMonth.toString(),
                fontSize = 14.dp.toSp(density),
                lineHeight = 12.dp.toSp(density),
                modifier = Modifier.padding(start = 4.dp, top = 4.dp),
            )
            if (notesCount != 0) {
                NotesCounter(
                    notesCount = notesCount,
                    modifier = Modifier
                        .padding(end = 4.dp, bottom = 3.dp)
                        .align(Alignment.BottomEnd),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DayTodayPreview() {
    Day(
        modifier = Modifier.width(50.dp),
        day = LocalDate.now(),
        isToday = true,
        isDaySelected = false,
        inSelectedMonth = true,
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun DayNotTodayInSelectedPreview() {
    Day(
        modifier = Modifier.width(50.dp),
        day = LocalDate.now(),
        isToday = false,
        isDaySelected = false,
        inSelectedMonth = true,
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun DayNotTodayNotSelectedPreview() {
    Day(
        modifier = Modifier.width(50.dp),
        day = LocalDate.now(),
        isToday = false,
        isDaySelected = false,
        inSelectedMonth = false,
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun DayNotTodaySelectedPreview() {
    Day(
        modifier = Modifier.width(50.dp),
        day = LocalDate.now(),
        isToday = false,
        isDaySelected = true,
        inSelectedMonth = true,
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun DayNotTodayInSelectedSomeNotesPreview() {
    Day(
        modifier = Modifier.width(50.dp),
        day = LocalDate.now(),
        isToday = false,
        isDaySelected = false,
        inSelectedMonth = true,
        notesCount = 37,
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun DayNotTodayInSelectedOneNotePreview() {
    Day(
        modifier = Modifier.width(50.dp),
        day = LocalDate.now(),
        isToday = false,
        isDaySelected = false,
        inSelectedMonth = true,
        notesCount = 7,
    ) {}
}
