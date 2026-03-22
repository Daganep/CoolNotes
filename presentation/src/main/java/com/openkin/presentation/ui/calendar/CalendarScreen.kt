package com.openkin.presentation.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.calendar.widget.Calendar
import com.openkin.presentation.ui.calendar.widget.CalendarTopBar
import com.openkin.presentation.ui.calendar.widget.MonthSelector
import com.openkin.presentation.ui.notesboard.widgets.AddNoteFloatButton
import com.openkin.presentation.ui.notesboard.widgets.BottomGradientDivider
import com.openkin.presentation.ui.notesboard.widgets.HorizontalSimpleNote
import com.openkin.presentation.ui.notesboard.widgets.TopGradientDivider
import com.openkin.presentation.ui.theme.emptyResultText
import com.openkin.presentation.ui.theme.white
import org.koin.androidx.compose.koinViewModel

@Composable
fun CalendarScreen(routing: IAppRouting) {
    CalendarScreen(
        viewModel = koinViewModel(),
        routing = routing,
    )
}

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    routing: IAppRouting,
) {
    val state by viewModel.viewState.collectAsState()
    val lazyListState = rememberLazyListState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        val (
            topBar,
            monthSelector,
            calendar,
            notesList,
            topGradientDivider,
            bottomGradientDivider,
            addNoteButton,
        ) = createRefs()

        CalendarTopBar(
            onTodayClick = { viewModel.setCurrentDaysList() },
            today = state.currentDay,
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(anchor = parent.top)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.value(56.dp)
                }
        )

        MonthSelector(
            selectedDate = state.selectedMonth,
            onMonthSelected = { selectedDate ->
                viewModel.onMonthChanged(selectedDate)
            },
            modifier = Modifier
                .constrainAs(monthSelector) {
                    top.linkTo(anchor = topBar.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                },
        )

        Calendar(
            listOfDays = state.listOfDays,
            currentDay = state.currentDay,
            selectedDate = state.selectedMonth,
            selectedDay = state.selectedDay,
            notesCount = state.notesCount,
            onDayClicked = { day -> viewModel.onSelectedDayChanged(day) },
            modifier = Modifier
                .constrainAs(calendar) {
                    top.linkTo(anchor = monthSelector.bottom, margin = 8.dp)
                    start.linkTo(anchor = parent.start, margin = 16.dp)
                    end.linkTo(anchor = parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
        )

        Box(
            modifier = Modifier
                .constrainAs(notesList) {
                    top.linkTo(anchor = calendar.bottom, margin = 8.dp)
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = parent.start, margin = 16.dp)
                    end.linkTo(anchor = parent.end, margin = 16.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            contentAlignment = Alignment.Center,
        ) {
            if (state.loadingInProgress) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            } else if (state.notesList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 8.dp),
                    state = lazyListState,
                ) {
                    items(items = state.notesList, key = { it.id }) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            contentAlignment = Alignment.CenterEnd,
                        ) {
                            HorizontalSimpleNote(item, routing::editNote)
                        }
                    }
                }
            } else {
                Text(
                    text = stringResource(R.string.notes_screen_empty_list),
                    style = MaterialTheme.typography.emptyResultText,
                )
            }
        }

        // Верхний градиент-разделитель
        TopGradientDivider(
            modifier = Modifier
                .constrainAs(topGradientDivider) {
                    top.linkTo(anchor = calendar.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    width = Dimension.fillToConstraints
                }
        )
        // Нижний градиент-разделитель
        BottomGradientDivider(
            modifier = Modifier
                .constrainAs(bottomGradientDivider) {
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    width = Dimension.fillToConstraints
                }
        )
        AddNoteFloatButton(
            onClick = { routing.addNote(state.selectedDay) },
            modifier = Modifier
                .constrainAs(addNoteButton) {
                    bottom.linkTo(parent.bottom, margin = 32.dp)
                    end.linkTo(anchor = parent.end, margin = 16.dp)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                },
        )
    }

    LaunchedEffect(true) {
        viewModel.setCurrentDaysList()
        viewModel.onLoadStoredSelectedDay()
    }
}
