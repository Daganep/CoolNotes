package com.openkin.presentation.ui.calendar.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.domain.model.DaysList
import com.openkin.domain.utils.DAYS_IN_WEEK
import java.time.LocalDate

@Composable
fun Calendar(
    listOfDays: List<LocalDate>,
    currentDay: LocalDate,
    selectedDate: LocalDate,
    selectedDay: LocalDate,
    notesCount: List<Int>,
    modifier: Modifier,
    onDayClicked: (LocalDate) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(DAYS_IN_WEEK),
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        itemsIndexed(items = listOfDays, key = { _, item -> item }) { index, item ->
            val count: Int = notesCount.getOrNull(index) ?: 0
            Day(
                modifier = Modifier,//.animateItem(),
                day = item,
                isToday = item == currentDay,
                inSelectedMonth = item.month == selectedDate.month,
                isDaySelected = item == selectedDay,
                notesCount = count,
                onDayClicked = { day -> onDayClicked(day) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarPreview() {
    val daysList = DaysList()
    Calendar(
        listOfDays = daysList.getDaysList(),
        currentDay = daysList.getToday(),
        modifier = Modifier,
        selectedDate = daysList.getToday(),
        selectedDay = daysList.getToday(),
        notesCount = listOf(0,0,5,0,0,1,4,0),
        onDayClicked = {},
    )
}
